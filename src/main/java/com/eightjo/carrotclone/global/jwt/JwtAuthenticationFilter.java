package com.eightjo.carrotclone.global.jwt;

import com.eightjo.carrotclone.global.dto.http.DefaultRes;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 헤더의 토큰 가져오기
        String access_token = jwtUtil.resolveToken(request, JwtUtil.ACCESS_TOKEN);
        String refresh_token = jwtUtil.resolveToken(request, JwtUtil.REFRESH_TOKEN);

        if (access_token != null) {//엑세스 토큰이 있으면
            if (jwtUtil.validateToken(access_token, jwtUtil.getAccessKey())) {//엑세스 키를 확인한다.
                setAuthentication(jwtUtil.getUserInfoFromToken(access_token, jwtUtil.getAccessKey())); //유저 정보 저장
            } else if (refresh_token != null) { //엑세스키가 만료 -> 리프레쉬 키가 있나? 있다.
                boolean isRefreshToken = jwtUtil.refreshTokenValidation(refresh_token);

                if (isRefreshToken) { //리프레쉬 토큰 유효? yes
                    String userId = jwtUtil.getUserInfoFromToken(refresh_token, jwtUtil.getRefreshKey());
                    String newAccessToken = jwtUtil.createToken(userId, jwtUtil.getAccessKey(), JwtUtil.ACCESS_TOKEN);
                    jwtUtil.setHeaderAccessToken(response, newAccessToken);

                    // 쿠키에 refreshToken을 httpOnly 방식으로 저장
                    String newRefreshToken = jwtUtil.createToken(userId, jwtUtil.getAccessKey(), JwtUtil.REFRESH_TOKEN);
                    newRefreshToken = newRefreshToken.substring(7);
                    Cookie cookie = new Cookie(JwtUtil.REFRESH_TOKEN, newRefreshToken);
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    setAuthentication(userId);
                } else {//리프레쉬 토큰 유효? no
                    jwtExceptionHandler(response, "Refresh 토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED.value());
                    return;
                }
            } else { //리프레쉬 토큰이 없음
                jwtExceptionHandler(response, "Access 토큰이 만료되었습니다. Refresh 토큰을 함께 보내주세요.", HttpStatus.FORBIDDEN.value());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 예외 처리 핸들러
    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json;charset=UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(new DefaultRes<>(msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

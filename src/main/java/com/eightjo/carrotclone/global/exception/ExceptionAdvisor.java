package com.eightjo.carrotclone.global.exception;

import com.eightjo.carrotclone.global.dto.http.DefaultDataRes;
import com.eightjo.carrotclone.global.dto.http.DefaultRes;
import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ExceptionAdvisor {
    /**
     * CustomException 처리
     */
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> handleCustomException(CustomException ex) {
        String msg = ex.getMsg();
        int statusCode = ex.getStatusCode();

        return ResponseEntity.status(statusCode).body(new DefaultRes<>(msg));
    }

    /**
     * Valid 예외 처리
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Object> signValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        Map<String, String> errorMap = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(new DefaultDataRes<>(ResponseMessage.CREATED_USER_FAIL, errorMap));
    }

    /**
     * 이미지 파일 미 업로드 시
     */
    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<?> handleBindException(BindException ex) {
        return ResponseEntity.badRequest().body(new DefaultRes<>(ResponseMessage.WRONG_FORMAT));
    }
}

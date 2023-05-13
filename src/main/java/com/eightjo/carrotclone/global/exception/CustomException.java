package com.eightjo.carrotclone.global.exception;

public class CustomException extends RuntimeException{
    private String msg;
    private int statusCode;

    public CustomException(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode=statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getMsg() {
        return this.msg;
    }
}

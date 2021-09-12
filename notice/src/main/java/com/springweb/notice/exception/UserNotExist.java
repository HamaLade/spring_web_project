package com.springweb.notice.exception;

public class UserNotExist extends RuntimeException{
    public UserNotExist(String message) {
        super(message);
    }
}

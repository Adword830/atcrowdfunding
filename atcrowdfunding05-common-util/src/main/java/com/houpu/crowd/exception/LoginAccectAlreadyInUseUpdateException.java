package com.houpu.crowd.exception;

public class LoginAccectAlreadyInUseUpdateException extends RuntimeException{

    public LoginAccectAlreadyInUseUpdateException() {
        super();
    }

    public LoginAccectAlreadyInUseUpdateException(String message) {
        super(message);
    }

    public LoginAccectAlreadyInUseUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAccectAlreadyInUseUpdateException(Throwable cause) {
        super(cause);
    }

    protected LoginAccectAlreadyInUseUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

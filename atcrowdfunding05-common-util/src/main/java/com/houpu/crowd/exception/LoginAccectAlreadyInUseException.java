package com.houpu.crowd.exception;

public class LoginAccectAlreadyInUseException extends RuntimeException {

    public LoginAccectAlreadyInUseException() {
        super();
    }

    public LoginAccectAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAccectAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAccectAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    protected LoginAccectAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

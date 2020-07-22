package com.houpu.crowd.exception;

public class AccessPermissionsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AccessPermissionsException() {
        super();
    }

    public AccessPermissionsException(String message) {
        super(message);
    }

    public AccessPermissionsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessPermissionsException(Throwable cause) {
        super(cause);
    }

    protected AccessPermissionsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

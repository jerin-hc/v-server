package com.ij3rry.vserver.exceptions;

public class InvalidProtocolException extends RuntimeException {
    public InvalidProtocolException(String message) {
        super(message);
    }

    public InvalidProtocolException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.ij3rry.vserver.exceptions;

public class InvalidRequestMapperException extends RuntimeException {
    public InvalidRequestMapperException(String message) {
        super(message);
    }

    public InvalidRequestMapperException(String message, Throwable cause) {
        super(message, cause);
    }
}

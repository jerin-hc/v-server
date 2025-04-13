package com.ij3rry.vserver.http.exceptions;

public class InvalidHttpRequest extends Exception{

    public InvalidHttpRequest(String message) {
        super(message);
    }

    public InvalidHttpRequest(String message, Throwable cause) {
        super(message, cause);
    }
}

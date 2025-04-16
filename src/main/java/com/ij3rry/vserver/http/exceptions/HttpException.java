package com.ij3rry.vserver.http.exceptions;

import com.ij3rry.vserver.http.enums.HttpResponseStatus;
import lombok.Getter;

@Getter
public class HttpException extends Exception{

    public HttpResponseStatus status;

    public HttpException(String message, HttpResponseStatus status) {
        super(message);
        this.status = status;
    }

    public HttpException(String message, Throwable cause,HttpResponseStatus status) {
        super(message, cause);
        this.status = status;
    }
}

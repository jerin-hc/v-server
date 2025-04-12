package com.ij3rry.vserver.http.data;

import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStream;

@Data
public class HttpRequest {
    private final HttpRequestHeader requestHeader;
    private final InputStream inputStream;

    public HttpRequest(HttpRequestHeader requestHeader, InputStream inputStream) {
        this.requestHeader = requestHeader;
        this.inputStream = inputStream;
    }
}

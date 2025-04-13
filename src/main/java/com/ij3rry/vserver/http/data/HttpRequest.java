package com.ij3rry.vserver.http.data;

import lombok.Data;

@Data
public class HttpRequest {
    private final HttpRequestHeader requestHeader;

    public HttpRequest(HttpRequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }
}

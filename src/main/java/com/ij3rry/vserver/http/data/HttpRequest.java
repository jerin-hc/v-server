package com.ij3rry.vserver.http.data;

import com.ij3rry.vserver.enums.Protocol;
import com.ij3rry.vserver.http.enums.HttpMethod;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class HttpRequest {
    private HttpMethod method;
    private String endpoint;
    private Protocol protocol;
    private final Map<String,String> headers = new HashMap<>();
    private final Map<String,String> pathParams = new HashMap<>();
    private final Map<String,String> pathVariable = new HashMap<>();
}

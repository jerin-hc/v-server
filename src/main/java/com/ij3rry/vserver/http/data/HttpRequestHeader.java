package com.ij3rry.vserver.http.data;

import com.ij3rry.vserver.enums.Protocol;
import lombok.Data;
import java.util.Map;

@Data
public class HttpRequestHeader {
    private String method;
    private String endpoint;
    private Protocol protocol;
    private Map<String,String> headers;
    private Map<String,Object> params;
}

package com.ij3rry.vserver.http.data;

import com.ij3rry.vserver.http.enums.HttpResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HttpResponse {
    private HttpResponseStatus status = HttpResponseStatus.OK;
    private HttpContentType contentType = HttpContentType.JSON;
    private Object body = new Object();

    public HttpResponse(HttpResponseStatus status){
        this.status = status;
    }

    public HttpResponse(HttpResponseStatus status, Object body){
        this.status = status;
        this.body = body;
    }
}

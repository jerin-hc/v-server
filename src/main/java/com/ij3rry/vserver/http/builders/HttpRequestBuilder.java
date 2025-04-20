package com.ij3rry.vserver.http.builders;

import com.ij3rry.vserver.builders.RequestBuilder;
import com.ij3rry.vserver.data.ServerContext;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.readers.HttpRequestReader;

public class HttpRequestBuilder implements RequestBuilder {
    private static HttpRequestBuilder instance;

    private HttpRequestBuilder(){}

    public static HttpRequestBuilder getInstance(){
        if( instance == null){
            instance = new HttpRequestBuilder();
        }
        return instance;
    }

    @Override
    public void build(ServerContext context){
        if (context instanceof HttpContext httpContext) {
            HttpRequestReader.getInstance().read(httpContext);
        }
    }
}

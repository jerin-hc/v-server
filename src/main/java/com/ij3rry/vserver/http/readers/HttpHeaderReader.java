package com.ij3rry.vserver.http.readers;

import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.enums.HttpResponseStatus;
import com.ij3rry.vserver.http.utils.HttpServerUtils;
import com.ij3rry.vserver.readers.HeaderReader;
import com.ij3rry.vserver.utils.ServerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class HttpHeaderReader implements HeaderReader {

    private HttpHeaderReader(){}

    private static HttpHeaderReader instance;

    public static HttpHeaderReader getInstance(){
        if(instance == null){
            instance = new HttpHeaderReader();
        }
        return instance;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHeaderReader.class);

    @Override
    public void read(HttpContext httpContext) {
        try{
            InputStream inputStream = httpContext.getInputStream();
            Map<String, String> headers = new HashMap<>();

            String line = null;
            while (!(line = ServerUtils.readLine(inputStream)).isEmpty()){
                String[] params = line.split(":\\s");
                if( params.length == 2 ){
                    headers.put(params[0],params[1]);
                }
            }
            httpContext.getHttpRequest().getRequestHeader().setHeaders(headers);
            LOGGER.debug("HTTP/1.1 Headers {}",headers);
        } catch (IOException e) {
            HttpServerUtils.generateErrorHeader(HttpResponseStatus.BAD_REQUEST,httpContext);
        }
    }
}

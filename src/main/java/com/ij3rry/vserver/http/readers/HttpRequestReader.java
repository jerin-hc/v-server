package com.ij3rry.vserver.http.readers;

import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.data.HttpRequest;
import com.ij3rry.vserver.http.enums.HttpResponseStatus;
import com.ij3rry.vserver.http.utils.HttpServerUtils;
import com.ij3rry.vserver.readers.RequestReader;
import com.ij3rry.vserver.utils.ServerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


public class HttpRequestReader implements RequestReader {

    private HttpRequestReader(){}

    private static HttpRequestReader instance;

    public static HttpRequestReader getInstance(){
        if(instance == null){
            instance = new HttpRequestReader();
        }
        return instance;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestReader.class);

    @Override
    public void read(HttpContext httpContext) {
        try{
            InputStream inputStream = httpContext.getInputStream();

            extractPathParam(httpContext.getHttpRequest());

            extractedHeader(inputStream, httpContext.getHttpRequest().getHeaders());


            LOGGER.debug("HTTP/1.1 Headers {}",httpContext.getHttpRequest().getHeaders());
        } catch (IOException e) {
            HttpServerUtils.generateErrorHeader(HttpResponseStatus.BAD_REQUEST,httpContext);
        }
    }

    private static void extractedHeader(InputStream inputStream, Map<String, String> headers) throws IOException {
        String line = null;
        while (!(line = ServerUtils.readLine(inputStream)).isEmpty()){
            String[] params = line.split(":\\s");
            if( params.length == 2 ){
                headers.put(params[0],params[1]);
            }
        }
    }

    private void extractPathParam(HttpRequest request) {
        String[] params = request.getEndpoint().split("\\?");
        if (params.length == 2) {
            Map<String,String> queryParams = request.getPathParams();
            String[] variables = params[1].split("&");
            for (String variable : variables) {
                String[] split = variable.split("=");
                if (split.length == 2) {
                    queryParams.put(split[0], split[1]);
                }else{
                    queryParams.put(split[0], null);
                }
            }
            request.setEndpoint(params[0]);
        }
    }
}

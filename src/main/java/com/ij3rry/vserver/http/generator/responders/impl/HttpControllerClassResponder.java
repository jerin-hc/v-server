package com.ij3rry.vserver.http.generator.responders.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ij3rry.vserver.http.controller.RESTController;
import com.ij3rry.vserver.http.data.HttpContentType;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.data.HttpResponse;
import com.ij3rry.vserver.http.enums.HttpMethod;
import com.ij3rry.vserver.http.enums.HttpResponseStatus;
import com.ij3rry.vserver.http.generator.responders.HttpResponder;
import com.ij3rry.vserver.http.holders.ControllerClassHolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpControllerClassResponder extends HttpResponder {
    private static HttpControllerClassResponder httpControllerClassResponder;

    private HttpControllerClassResponder(){
    }

    public static HttpControllerClassResponder getInstance(){
        if(httpControllerClassResponder == null){
            httpControllerClassResponder = new HttpControllerClassResponder();
        }
        return httpControllerClassResponder;
    }

    @Override
    public void generateResponse(HttpContext context, Map<String, Object> endpointConfigs, HttpMethod httpMethod) throws IOException {
        String classPath = (String) endpointConfigs.get("path");
        byte[] jsonBytes = new byte[0];
        String headers = "";
        try{
            Object obj = ControllerClassHolder.getClazz().get(httpMethod.name()).get(context.getHttpRequest().getRequestHeader().getEndpoint());
            if (obj instanceof RESTController) {
                HttpResponse respone;
                if (httpMethod.equals(HttpMethod.POST)) {
                    respone = ((RESTController) obj).doPost(context);
                }else if(httpMethod.equals(HttpMethod.PUT)){
                    respone = ((RESTController) obj).doPut(context);
                }else if(httpMethod.equals(HttpMethod.DELETE)){
                    respone = ((RESTController) obj).doDelete(context);
                }else if(httpMethod.equals(HttpMethod.PATCH)){
                    respone = ((RESTController) obj).doPatch(context);
                }else{
                    respone = ((RESTController) obj).doGet(context);
                }
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(respone.getBody());
                jsonBytes = json.getBytes(StandardCharsets.UTF_8);
                headers = generateHeader(jsonBytes, respone.getStatus());
            }else{
                headers = generateErrorHeader(HttpResponseStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            headers = generateErrorHeader(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
        OutputStream out = context.getOutputStream();
        out.write(headers.getBytes(StandardCharsets.UTF_8));
        out.write(jsonBytes);
    }

    private static String generateHeader(byte[] josn, HttpResponseStatus status) throws IOException {
        HttpContentType contentType = HttpContentType.fromExtension("json");
        String headers =
                "HTTP/1.1 "+status.code()+" "+status.reason()+"\r\n" +
                        "Content-Type: "+contentType.getMimeType()+"; charset=UTF-8\r\n" +
                        "Content-Length: " + josn.length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n";
        return headers;
    }

    private static String generateErrorHeader(HttpResponseStatus status) throws IOException {
        HttpContentType contentType = HttpContentType.fromExtension("json");
        String headers =
                "HTTP/1.1 "+status.code()+" "+status.reason()+"\r\n" +
                        "Content-Type: "+contentType.getMimeType()+"; charset=UTF-8\r\n" +
                        "Content-Length: " + 0 + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n";
        return headers;
    }
}

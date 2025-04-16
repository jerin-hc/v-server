package com.ij3rry.vserver.http.generator.responders.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ij3rry.vserver.http.controller.RESTController;
import com.ij3rry.vserver.http.data.HttpContentType;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.data.HttpResponse;
import com.ij3rry.vserver.http.enums.HttpMethod;
import com.ij3rry.vserver.http.enums.HttpResponseStatus;
import com.ij3rry.vserver.http.exceptions.HttpException;
import com.ij3rry.vserver.http.generator.responders.HttpResponder;
import com.ij3rry.vserver.http.holders.ControllerClassHolder;

import java.io.IOException;
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
    public void generateResponse(HttpContext context, Map<String, Object> endpointConfigs, HttpMethod httpMethod) throws IOException, HttpException {
        byte[] jsonBytes;
        String headers;

        Object obj = ControllerClassHolder.getClazz().get(httpMethod.name()).get(context.getHttpRequest().getRequestHeader().getEndpoint());
        if (obj instanceof RESTController) {
            HttpResponse response;
            if (httpMethod.equals(HttpMethod.POST)) {
                response = ((RESTController) obj).doPost(context);
            } else if (httpMethod.equals(HttpMethod.PUT)) {
                response = ((RESTController) obj).doPut(context);
            } else if (httpMethod.equals(HttpMethod.DELETE)) {
                response = ((RESTController) obj).doDelete(context);
            } else if (httpMethod.equals(HttpMethod.PATCH)) {
                response = ((RESTController) obj).doPatch(context);
            } else {
                response = ((RESTController) obj).doGet(context);
            }
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(response.getBody());
            jsonBytes = json.getBytes(StandardCharsets.UTF_8);
            headers = generateHeader(jsonBytes, response.getStatus());
        } else {
            throw new HttpException("Unknown error while generating response", HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
        OutputStream out = context.getOutputStream();
        out.write(headers.getBytes(StandardCharsets.UTF_8));
        out.write(jsonBytes);
    }

    private static String generateHeader(byte[] josn, HttpResponseStatus status) {
        HttpContentType contentType = HttpContentType.fromExtension("json");
        String headers =
                "HTTP/1.1 "+status.code()+" "+status.reason()+"\r\n" +
                        "Content-Type: "+contentType.getMimeType()+"; charset=UTF-8\r\n" +
                        "Content-Length: " + josn.length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n";
        return headers;
    }
}

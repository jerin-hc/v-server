package com.ij3rry.vserver.http.generator.responders.impl;

import com.ij3rry.vserver.http.data.HttpContentType;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.enums.HttpMethod;
import com.ij3rry.vserver.http.generator.HttpResponseGenerator;
import com.ij3rry.vserver.http.generator.responders.HttpResponder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpStaticFileResponder extends HttpResponder {

    private static HttpStaticFileResponder httpStaticFileResponder;

    private HttpStaticFileResponder(){
    }

    public static HttpStaticFileResponder getInstance(){
        if(httpStaticFileResponder == null){
            httpStaticFileResponder = new HttpStaticFileResponder();
        }
        return httpStaticFileResponder;
    }

    @Override
    public void generateResponse(HttpContext context, Map<String, Object> endpointConfigs, HttpMethod httpMethod) throws IOException {
        String fileName = context.getHttpRequest().getRequestHeader().getEndpoint();
        String path = (String) endpointConfigs.get("path")+fileName;
        InputStream inputStream = HttpResponseGenerator.class.getResourceAsStream(path);

        String[] splitFileName = fileName.split("\\.");
        String headers;
        if (splitFileName.length == 2) {
            headers = generateHeader(inputStream, splitFileName[1]);
        }else{
            headers = generateHeader(inputStream, null);
        }

        OutputStream out = context.getOutputStream();
        out.write(headers.getBytes(StandardCharsets.UTF_8));
        out.write(inputStream.readAllBytes());

    }

    private static String generateHeader(InputStream inputStream,String fileType) throws IOException {
        HttpContentType contentType = HttpContentType.fromExtension(fileType);
        if(contentType == null){
            contentType = HttpContentType.fromExtension("txt");
        }
        String headers =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: "+contentType.getMimeType()+"; charset=UTF-8\r\n" +
                        "Content-Length: " + inputStream.available() + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n";
        return headers;
    }
}

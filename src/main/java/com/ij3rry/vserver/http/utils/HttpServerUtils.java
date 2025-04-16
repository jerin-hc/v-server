package com.ij3rry.vserver.http.utils;

import com.ij3rry.vserver.http.data.HttpContentType;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.enums.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpServerUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerUtils.class);


    public static void generateErrorHeader(HttpResponseStatus status, HttpContext context){
        HttpContentType contentType = HttpContentType.fromExtension("json");
        String headers =
                "HTTP/1.1 "+status.code()+" "+status.reason()+"\r\n" +
                        "Content-Type: "+contentType.getMimeType()+"; charset=UTF-8\r\n" +
                        "Content-Length: " + 0 + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n";
        OutputStream out = context.getOutputStream();
        try{
            out.write(headers.getBytes(StandardCharsets.UTF_8));
        }catch (IOException ex){
            LOGGER.error("Error while writing response header with status "+status);
        }
    }
}

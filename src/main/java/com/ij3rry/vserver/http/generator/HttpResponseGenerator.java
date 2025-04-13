package com.ij3rry.vserver.http.generator;

import com.ij3rry.App;
import com.ij3rry.vserver.data.ServerContext;
import com.ij3rry.vserver.generators.ResponseGenerator;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.enums.HttpMethod;
import com.ij3rry.vserver.http.exceptions.InvalidHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpResponseGenerator implements ResponseGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponseGenerator.class);

    private static HttpResponseGenerator instance;

    private HttpResponseGenerator(){}

    public static HttpResponseGenerator getInstance(){
        if(instance == null){
            instance = new HttpResponseGenerator();
        }
        return instance;
    }

    @Override
    public void generate(ServerContext context) throws InvalidHttpRequest {
        HttpContext httpContext = (HttpContext) context;
        HttpMethod method = httpContext.getHttpRequest().getRequestHeader().getMethod();
        Map<String, Object> routeConfigs = (Map<String, Object>) context.getServerConfig().get("routes");
        if(routeConfigs == null){
            throw new InvalidHttpRequest("routing config not found");
        }

        List<Map<String,Object>> routes = (List) routeConfigs.get(method.name());
        if ( routes.isEmpty() ){
            throw new InvalidHttpRequest("routing config not found for the http method");
        }

        Map<String, Object> endpointConfigs = routes.stream().filter(stringObjectMap ->
                stringObjectMap.get("endpoint").equals(((HttpContext) context)
                        .getHttpRequest().getRequestHeader()
                        .getEndpoint()))
                .findFirst().get();
        if( endpointConfigs.isEmpty() ){
            throw new InvalidHttpRequest("routing config not found for the endpoint");
        }

        String fileName = ((HttpContext) context).getHttpRequest().getRequestHeader().getEndpoint();
        String path = (String) endpointConfigs.get("path")+fileName;
        try (InputStream inputStream = HttpResponseGenerator.class.getResourceAsStream(path)) {
            String headers =
                    "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/html; charset=UTF-8\r\n" +
                            "Content-Length: " + inputStream.available() + "\r\n" +
                            "Connection: close\r\n" +
                            "\r\n";
            OutputStream out = ((HttpContext) context).getOutputStream();
            out.write(headers.getBytes(StandardCharsets.UTF_8));
            out.write(inputStream.readAllBytes());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

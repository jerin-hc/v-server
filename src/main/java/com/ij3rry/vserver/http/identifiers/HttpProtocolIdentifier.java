package com.ij3rry.vserver.http.identifiers;

import com.ij3rry.vserver.data.ServerContext;
import com.ij3rry.vserver.enums.Protocol;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.enums.HttpMethod;
import com.ij3rry.vserver.identifiers.ProtocolIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class HttpProtocolIdentifier implements ProtocolIdentifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpProtocolIdentifier.class);

    @Override
    public ServerContext identify(String firstLine, InputStream inputStream, OutputStream outputStream, Map<String, Object> serverConfig) {
        String[] line = firstLine.split("\\s");
        if (line.length == 3 && line[2].equals(Protocol.HTTP_1_1.toString())) {
            if(serverConfig == null || serverConfig.get("http") == null){
                LOGGER.info("/http/request-mapper.yaml is not loaded");
            }
            Map<String,Object> httpRouteConfig = (Map<String, Object>) serverConfig.get("http");
            Protocol p = Protocol.HTTP_1_1;
            HttpContext httpContext = new HttpContext.HttpContextBuilder()
                    .setInputStream(inputStream)
                    .setOutputStream(outputStream)
                    .setProtocol(p)
                    .setConfig(httpRouteConfig)
                    .build();
            httpContext.getHttpRequest().setProtocol(p);
            httpContext.getHttpRequest().setEndpoint(line[1]);
            httpContext.getHttpRequest().setMethod(HttpMethod.valueOf(line[0]));
            return httpContext;
        }
        throw new RuntimeException("Unknown protocol "+firstLine);
    }
}

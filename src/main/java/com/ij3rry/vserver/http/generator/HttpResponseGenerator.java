package com.ij3rry.vserver.http.generator;

import com.ij3rry.vserver.data.ServerContext;
import com.ij3rry.vserver.generators.ResponseGenerator;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.enums.GeneratorType;
import com.ij3rry.vserver.http.enums.HttpMethod;
import com.ij3rry.vserver.http.enums.HttpResponseStatus;
import com.ij3rry.vserver.http.exceptions.HttpException;
import com.ij3rry.vserver.http.factories.HttpResponderFactory;
import com.ij3rry.vserver.http.utils.HttpServerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public void generate(ServerContext context) {
        assert context instanceof HttpContext;
        try{
            HttpContext httpContext = (HttpContext) context;
            HttpMethod method = httpContext.getHttpRequest().getRequestHeader().getMethod();
            Map<String, Object> routeConfigs = (Map<String, Object>) context.getServerConfig().get("routes");
            if (routeConfigs == null) {
                throw new HttpException("routing config not found", HttpResponseStatus.NOT_FOUND);
            }

            List<Map<String, Object>> routes = (List) routeConfigs.get(method.name());
            if (routes == null || routes.isEmpty()) {
                throw new HttpException("routing config not found for the http method", HttpResponseStatus.NOT_FOUND);
            }

            Optional<Map<String, Object>> endpointConfigs = routes.stream().filter(stringObjectMap ->
                            stringObjectMap.get("endpoint").equals(((HttpContext) context)
                                    .getHttpRequest().getRequestHeader()
                                    .getEndpoint()))
                    .findFirst();
            if (endpointConfigs.isEmpty()) {
                throw new HttpException("routing config not found for the endpoint", HttpResponseStatus.NOT_FOUND);
            }

            GeneratorType generatorType = GeneratorType.fromString((String) endpointConfigs.get().get("type"));
            HttpResponderFactory.getHttpResponder(generatorType).generateResponse((HttpContext) context, endpointConfigs.get(), method);
        }catch (HttpException ex){
            HttpServerUtils.generateErrorHeader(ex.getStatus(), (HttpContext) context);
        } catch (Exception e) {
            HttpServerUtils.generateErrorHeader(HttpResponseStatus.INTERNAL_SERVER_ERROR, (HttpContext) context);
        }
    }
}

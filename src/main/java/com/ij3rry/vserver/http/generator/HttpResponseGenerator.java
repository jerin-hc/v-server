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
            HttpMethod method = httpContext.getHttpRequest().getMethod();
            Map<String, Object> routeConfigs = (Map<String, Object>) context.getServerConfig().get("routes");
            if (routeConfigs == null) {
                throw new HttpException("routing config not found", HttpResponseStatus.NOT_FOUND);
            }

            List<Map<String, Object>> routes = (List<Map<String, Object>>) routeConfigs.get(method.name());
            if (routes == null || routes.isEmpty()) {
                throw new HttpException("routing config not found for the http method", HttpResponseStatus.NOT_FOUND);
            }

            Map<String, Object> endpointConfigs=null;
            for( Map<String,Object> routeDetails : routes){
                String endpoint = (String) routeDetails.get("endpoint");
                if( httpContext.getHttpRequest().getEndpoint().equals(endpoint) || verifyAndExtractPathVariableEndpoint(endpoint, httpContext) ){
                    endpointConfigs = routeDetails;
                    break;
                }
            }

            if (endpointConfigs == null || endpointConfigs.isEmpty()) {
                throw new HttpException("routing config not found for the endpoint", HttpResponseStatus.NOT_FOUND);
            }

            GeneratorType generatorType = GeneratorType.fromString((String) endpointConfigs.get("type"));
            HttpResponderFactory.getHttpResponder(generatorType).generateResponse((HttpContext) context, endpointConfigs, method);
        }catch (HttpException ex){
            HttpServerUtils.generateErrorHeader(ex.getStatus(), (HttpContext) context);
        } catch (Exception e) {
            HttpServerUtils.generateErrorHeader(HttpResponseStatus.INTERNAL_SERVER_ERROR, (HttpContext) context);
        }
    }

    private static boolean verifyAndExtractPathVariableEndpoint(String endpoint, HttpContext httpContext) {
        String[] configEndpointSplit = endpoint.split("/");
        String[] requestEndpointSplit = httpContext.getHttpRequest().getEndpoint().split("/");
        if(configEndpointSplit.length != requestEndpointSplit.length)
            return false;
        for(int i=0;i<configEndpointSplit.length;i++){
            if (configEndpointSplit[i].startsWith("{") && configEndpointSplit[i].endsWith("}")) {
                String variableName = configEndpointSplit[i].replace("{","").replace("}","");
                httpContext.getHttpRequest().getPathVariable().put(variableName,requestEndpointSplit[i]);
                continue;
            }
            if (configEndpointSplit[i].equals(requestEndpointSplit[i]))
                continue;
            return false;
        }
        return true;
    }
}

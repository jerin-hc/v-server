package com.ij3rry.vserver.factories;

import com.ij3rry.vserver.builders.RequestBuilder;
import com.ij3rry.vserver.data.ServerContext;
import com.ij3rry.vserver.enums.Protocol;
import com.ij3rry.vserver.http.builders.HttpRequestBuilder;
import com.ij3rry.vserver.http.data.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BuilderFactory {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(BuilderFactory.class);


    public static RequestBuilder getBuilderFactory(ServerContext context){
        if (context instanceof HttpContext && ((HttpContext) context).getHttpRequest().getProtocol().equals(Protocol.HTTP_1_1)){
            LOGGER.debug("HttpRequest builder created for protocol {}",Protocol.HTTP_1_1);
            return HttpRequestBuilder.getInstance();
        }
        throw new InternalError("Unable to create builder for "+ context.getProtocol() +" protocol");
    }
}

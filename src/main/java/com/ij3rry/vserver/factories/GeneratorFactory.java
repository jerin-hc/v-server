package com.ij3rry.vserver.factories;

import com.ij3rry.vserver.data.ServerContext;
import com.ij3rry.vserver.enums.Protocol;
import com.ij3rry.vserver.generators.ResponseGenerator;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.generator.HttpResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratorFactory {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(GeneratorFactory.class);


    public static ResponseGenerator getGeneratorFactory(ServerContext context){
        if ( context instanceof HttpContext && ((HttpContext) context).getHttpRequest().getProtocol().equals(Protocol.HTTP_1_1)){
            LOGGER.debug("HttpRequest builder created for protocol {}",Protocol.HTTP_1_1);
            return HttpResponseGenerator.getInstance();
        }
        throw new InternalError("Unable to create generator for "+ context.getProtocol() +" protocol");
    }
}

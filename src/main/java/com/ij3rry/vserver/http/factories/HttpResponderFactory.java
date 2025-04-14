package com.ij3rry.vserver.http.factories;

import com.ij3rry.vserver.http.enums.GeneratorType;
import com.ij3rry.vserver.http.generator.HttpResponseGenerator;
import com.ij3rry.vserver.http.generator.responders.HttpResponder;
import com.ij3rry.vserver.http.generator.responders.impl.HttpStaticFileResponder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponderFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponderFactory.class);

    private HttpResponderFactory(){}

    public static HttpResponder getHttpResponder(GeneratorType type){
        if(type == null){
            throw new RuntimeException("Incompatible Generator type "+type);
        }
        if(type.equals(GeneratorType.FILE)){
            LOGGER.info("Responder created for Generator type {}",GeneratorType.FILE);
            return HttpStaticFileResponder.getInstance();
        }
        else{
            throw new RuntimeException("Generator not implemented for "+type);
        }
    }
}

package com.ij3rry.vserver.factories;

import com.ij3rry.vserver.builders.RequestBuilder;
import com.ij3rry.vserver.enums.Protocol;
import com.ij3rry.vserver.http.builders.HttpRequestBuilder;

public final class BuilderFactory {

    public static RequestBuilder getBuilderFactory(Protocol protocol){
        if (protocol.equals(Protocol.HTTP_1_1)){
            return new HttpRequestBuilder();
        }
        throw new InternalError("Unable to create builder for "+protocol.toString()+" protocol");
    }
}

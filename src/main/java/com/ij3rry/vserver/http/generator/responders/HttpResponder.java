package com.ij3rry.vserver.http.generator.responders;

import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.enums.HttpMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpResponder {

    public abstract void generateResponse(HttpContext context, Map<String, Object> endpointConfigs, HttpMethod httpMethod) throws IOException;
}

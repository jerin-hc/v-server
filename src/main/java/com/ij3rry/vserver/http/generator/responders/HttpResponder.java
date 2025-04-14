package com.ij3rry.vserver.http.generator.responders;

import com.ij3rry.vserver.http.data.HttpContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpResponder {

    public abstract void generateResponse(HttpContext context, Map<String, Object> endpointConfigs) throws IOException;

    public static final Map<String, String> contentTypeMapper = new HashMap<String, String>() {{
        put("html", "text/html");
        put("css", "text/css");
        put("js", "application/javascript");
        put("txt", "text/plain");
        put("xml", "application/xml");
        put("yaml", "application/x-yaml");
        put("yml", "application/x-yaml");
        put("jpg", "image/jpeg");
        put("jpeg", "image/jpeg");
        put("png", "image/png");
        put("gif", "image/gif");
        put("zip", "application/zip");
        put("tar", "application/x-tar");
        put("json", "application/json");
        put("mp4", "video/mp4");
        put("pdf", "application/pdf");
        put("svg", "image/svg+xml");
        put("woff2", "font/woff2");
    }};

}

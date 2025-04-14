package com.ij3rry.vserver.http.data;

import lombok.Getter;

import java.security.InvalidKeyException;

@Getter
public enum HttpContentType {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "application/javascript"),
    TXT("txt", "text/plain"),
    XML("xml", "application/xml"),
    YAML("yaml", "application/x-yaml"),
    YML("yml", "application/x-yaml"),
    JPG("jpg", "image/jpeg"),
    JPEG("jpeg", "image/jpeg"),
    PNG("png", "image/png"),
    GIF("gif", "image/gif"),
    ZIP("zip", "application/zip"),
    TAR("tar", "application/x-tar"),
    JSON("json", "application/json"),
    MP4("mp4", "video/mp4"),
    PDF("pdf", "application/pdf"),
    SVG("svg", "image/svg+xml"),
    WOFF2("woff2", "font/woff2");

    private final String extension;
    private final String mimeType;

    HttpContentType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public static HttpContentType fromExtension(String ext) throws RuntimeException {
        for (HttpContentType ct : values()) {
            if (ct.extension.equalsIgnoreCase(ext)) {
                return ct;
            }
        }
       throw new RuntimeException("Unsupported response type");
    }
}


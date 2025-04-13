package com.ij3rry.vserver.http.data;

import com.ij3rry.vserver.data.ServerContext;
import com.ij3rry.vserver.enums.Protocol;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class HttpContext extends ServerContext {
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private InputStream inputStream;
    private OutputStream outputStream;

    public HttpContext(HttpContextBuilder builder){
        this.httpRequest = builder.httpRequest;
        this.httpResponse = builder.httpResponse;
        this.inputStream = builder.inputStream;
        this.outputStream = builder.outputStream;
        this.serverConfig = builder.config;
        this.protocol = builder.protocol;
    }

    public static class HttpContextBuilder {
        private HttpRequest httpRequest;
        private HttpResponse httpResponse;
        private InputStream inputStream;
        private OutputStream outputStream;
        private Protocol protocol;
        private Map<String,Object> config;

        public HttpContextBuilder setHttpRequest(HttpRequest httpRequest) {
            this.httpRequest = httpRequest;
            return this;
        }

        public HttpContextBuilder setHttpResponse(HttpResponse httpResponse) {
            this.httpResponse = httpResponse;
            return this;
        }

        public HttpContextBuilder setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public HttpContextBuilder setOutputStream(OutputStream outputStream) {
            this.outputStream = outputStream;
            return this;
        }

        public HttpContextBuilder setProtocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public HttpContextBuilder setConfig(Map<String, Object> config) {
            this.config = config;
            return this;
        }

        public HttpContext build(){
            if(this.inputStream == null){
                throw new NullPointerException("InputStream should not be null");
            }
            if(this.outputStream == null){
                throw new NullPointerException("OutputStream should not be null");
            }
            if( this.httpRequest == null){
                HttpRequestHeader header = new HttpRequestHeader();
                this.httpRequest = new HttpRequest(header);
            }
            if( this.httpResponse == null){
                this.httpResponse = new HttpResponse();
            }
            return new HttpContext(this);
        }
    }
}

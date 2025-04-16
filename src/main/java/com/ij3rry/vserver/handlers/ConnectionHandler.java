package com.ij3rry.vserver.handlers;

import com.ij3rry.vserver.builders.RequestBuilder;
import com.ij3rry.vserver.concurrent.BoundedThreadExecutor;
import com.ij3rry.vserver.data.ServerContext;
import com.ij3rry.vserver.exceptions.InvalidRequestMapperException;
import com.ij3rry.vserver.factories.BuilderFactory;
import com.ij3rry.vserver.factories.GeneratorFactory;
import com.ij3rry.vserver.generators.ResponseGenerator;
import com.ij3rry.vserver.http.holders.ControllerClassHolder;
import com.ij3rry.vserver.identifiers.ProtocolIdentifier;
import com.ij3rry.vserver.utils.ServerUtils;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ConnectionHandler {
    private final int maxConcurrentTask;
    private final int timeOutMilliSec;
    private final int port;
    @Getter
    private final Map<String, Object> serverConfig;
    private static ProtocolIdentifier protocolIdentifier;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionHandler.class);

    private ConnectionHandler(ConnectionHandlerBuilder builder) {
        this.maxConcurrentTask = builder.maxConcurrentTask;
        this.timeOutMilliSec = builder.timeOutMilliSec;
        this.port = builder.port;
        this.serverConfig = builder.serverConfig;
    }

    public void start() throws IOException {
        ExecutorService executorService = BoundedThreadExecutor.newBoundedThreadExecutor(maxConcurrentTask,timeOutMilliSec);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            try (executorService) {
                LOGGER.info("server started listening at port : " + port);
                while (true) {
                    Socket s = serverSocket.accept();
                    executorService.submit(() -> handleRequest(s));
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void handleRequest(Socket socket) {
        try(InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();socket){

            String firstLine = ServerUtils.readLine(inputStream);
            if(firstLine.isEmpty()){
                LOGGER.error("Ignoring request with empty request line");
                return;
            }
            LOGGER.debug("Handling request {}",firstLine);
            ServerContext serverContext = protocolIdentifier.identify(firstLine,inputStream,outputStream,serverConfig);

            RequestBuilder requestBuilder = BuilderFactory.getBuilderFactory(serverContext);
            requestBuilder.build(serverContext);

            ResponseGenerator responseGenerator = GeneratorFactory.getGeneratorFactory(serverContext);
            responseGenerator.generate(serverContext);

        } catch (Exception e) {
            LOGGER.error(String.valueOf(e.fillInStackTrace()));
        }

    }

    public static class ConnectionHandlerBuilder{
        private int maxConcurrentTask = 1000;
        private int timeOutMilliSec = 500;
        private int port = 8080;
        private Map<String, Object> serverConfig;
        private boolean loadControllerClass;

        public ConnectionHandlerBuilder setMaxConcurrentTask(int maxConcurrentTask) {
            this.maxConcurrentTask = maxConcurrentTask;
            return this;
        }

        public ConnectionHandlerBuilder setTimeOutMilliSec(int timeOutMilliSec) {
            this.timeOutMilliSec = timeOutMilliSec;
            return this;
        }

        public ConnectionHandlerBuilder setPort(int port) {
            this.port = port;
            return this;
        }

        public ConnectionHandlerBuilder setServerConfig(Map<String, Object> serverConfig) {
            this.serverConfig = serverConfig;
            return this;
        }

        public ConnectionHandlerBuilder setupHttpServer(){
            try {
                Class<?> clazz = Class.forName("com.ij3rry.vserver.http.identifiers.HttpProtocolIdentifier");
                protocolIdentifier = (ProtocolIdentifier) clazz.getDeclaredConstructor().newInstance();
                LOGGER.info("ProtocolIdentifier class loaded properly {}", clazz.getCanonicalName());
            } catch (Exception e) {
                LOGGER.error("ProtocolIdentifier class didn't found, add http module");
                throw new RuntimeException(e);
            }
            try (InputStream inputStream = ConnectionHandler.class.getResourceAsStream("/http/request-mapper.yaml")) {
                this.serverConfig = new Yaml().load(inputStream);
                LOGGER.info("/http/request-mapper.yaml found with mapping {}", serverConfig.toString());
                ControllerClassHolder.loadAllControllerClass((Map<String, Object>) serverConfig.get("http"));
            } catch (Exception e) {
                throw new InvalidRequestMapperException("/http/request-mapper.yaml unavailable",e);
            }
            return this;
        }

        public ConnectionHandler build() throws IOException {
            return new ConnectionHandler(this);
        }
    }
}

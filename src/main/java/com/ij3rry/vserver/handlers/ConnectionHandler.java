package com.ij3rry.vserver.handlers;

import com.ij3rry.vserver.builders.RequestBuilder;
import com.ij3rry.vserver.concurrent.BoundedThreadExecutor;
import com.ij3rry.vserver.enums.Protocol;
import com.ij3rry.vserver.exceptions.InvalidRequestException;
import com.ij3rry.vserver.factories.BuilderFactory;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.data.HttpRequest;
import com.ij3rry.vserver.http.data.HttpRequestHeader;
import com.ij3rry.vserver.http.data.HttpResponse;
import com.ij3rry.vserver.utils.ServerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ConnectionHandler {
    private final int maxConcurrentTask;
    private final int timeOutMilliSec;
    private final int port;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionHandler.class);

    public ConnectionHandler(int maxConcurrentTask, int timeOutMilliSec, int port) {
        this.maxConcurrentTask = maxConcurrentTask;
        this.timeOutMilliSec = timeOutMilliSec;
        this.port = port;
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
            throw new RuntimeException(e);
        }
    }

    private void handleRequest(Socket socket) {
        try(InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();socket){
            HttpContext httpContext = new HttpContext.HttpContextBuilder()
                    .setInputStream(inputStream)
                    .setOutputStream(outputStream)
                    .build();

            String firstLine = ServerUtils.readLine(inputStream);
            if(firstLine.isEmpty()){
                LOGGER.error("Invalid request with empty request line");
                return;
            }
            LOGGER.debug("Handling request {}",firstLine);
            identifyRequest(firstLine, httpContext.getHttpRequest());

            RequestBuilder requestBuilder = BuilderFactory.getBuilderFactory(httpContext);
            requestBuilder.build(httpContext);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    // http protocol contains <HTTP_METHOD> <PATH> <HTTP_VERSION>
    private void identifyRequest(String firstLine, HttpRequest httpRequest) throws IOException {
        String[] line = firstLine.split("\\s");
        if (line.length == 3 && line[2].equals(Protocol.HTTP_1_1.toString())) {
            Protocol p = Protocol.HTTP_1_1;
            httpRequest.getRequestHeader().setProtocol(p);
            httpRequest.getRequestHeader().setEndpoint(line[1]);
            httpRequest.getRequestHeader().setMethod(line[0]);
        }
        /*
        add new here
         */
        if(httpRequest.getRequestHeader().getProtocol() == null){
            throw new InvalidRequestException("Failed to identify protocol");
        }
    }
}

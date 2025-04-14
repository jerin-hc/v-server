package com.ij3rry;

import com.ij3rry.vserver.handlers.ConnectionHandler;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        ConnectionHandler connectionHandler = new ConnectionHandler.ConnectionHandlerBuilder().setPort(8080).setMaxConcurrentTask(1000).setTimeOutMilliSec(500).setupHttpServer().build();

        try {
            connectionHandler.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

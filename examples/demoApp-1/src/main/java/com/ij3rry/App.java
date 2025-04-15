package com.ij3rry;

import com.ij3rry.vserver.handlers.ConnectionHandler;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        ConnectionHandler connectionHandler = new ConnectionHandler.ConnectionHandlerBuilder().setupHttpServer().build();
        connectionHandler.start();
    }
}

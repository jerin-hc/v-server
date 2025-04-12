package com.ij3rry;

import com.ij3rry.vserver.handlers.ConnectionHandler;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ConnectionHandler connectionHandler = new ConnectionHandler(500, 500, 8080);
        try {
            connectionHandler.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

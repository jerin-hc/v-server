package com.ij3rry.vserver.concurrent;

public class ConnectionTimeOutException extends Exception{

    ConnectionTimeOutException(String message){
        super("ConnectionTimeOutException : "+message);
    }
}

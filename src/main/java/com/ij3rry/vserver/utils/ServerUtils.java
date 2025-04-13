package com.ij3rry.vserver.utils;

import com.ij3rry.vserver.handlers.ConnectionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ServerUtils {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(ServerUtils.class);

    public static String readLine(InputStream inputStream) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int b;
        boolean seenCR = false;

        // finding \r\n EOL
        while ((b = inputStream.read()) != -1) {
            if (b == '\n') {
                break;
            } else if (b == '\r') {
                seenCR = true;
            } else {
                if (seenCR) {
                    outputStream.write(b);
                    break;
                }
                outputStream.write(b);
            }
        }
        return outputStream.toString(StandardCharsets.UTF_8);
    }

    public static String readLine(InputStream inputStream, int contentLength) throws IOException {
        return new String(inputStream.readNBytes(contentLength));
    }
}

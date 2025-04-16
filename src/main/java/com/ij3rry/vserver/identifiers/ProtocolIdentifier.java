package com.ij3rry.vserver.identifiers;

import com.ij3rry.vserver.data.ServerContext;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface ProtocolIdentifier {
    ServerContext identify(String firstLine, InputStream inputStream, OutputStream outputStream, Map<String,Object> serverConfig);
}

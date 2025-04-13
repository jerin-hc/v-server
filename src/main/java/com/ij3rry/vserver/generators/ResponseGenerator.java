package com.ij3rry.vserver.generators;

import com.ij3rry.vserver.data.ServerContext;
import com.ij3rry.vserver.http.exceptions.InvalidHttpRequest;

public interface ResponseGenerator {
    void generate(ServerContext context) throws InvalidHttpRequest;
}

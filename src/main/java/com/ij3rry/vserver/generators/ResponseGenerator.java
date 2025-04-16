package com.ij3rry.vserver.generators;

import com.ij3rry.vserver.data.ServerContext;
import com.ij3rry.vserver.http.exceptions.HttpException;

import java.io.IOException;

public interface ResponseGenerator {
    void generate(ServerContext context) throws HttpException, IOException;
}

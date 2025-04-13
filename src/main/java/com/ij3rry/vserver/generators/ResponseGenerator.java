package com.ij3rry.vserver.generators;

import com.ij3rry.vserver.data.ServerContext;

public interface ResponseGenerator<T> {
    T generate(ServerContext context);
}

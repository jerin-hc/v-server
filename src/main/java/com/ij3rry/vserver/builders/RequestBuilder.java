package com.ij3rry.vserver.builders;

import com.ij3rry.vserver.data.ServerContext;

public interface RequestBuilder {
    void build(ServerContext context);
}

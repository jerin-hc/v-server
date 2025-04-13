package com.ij3rry.vserver.builders;

import com.ij3rry.vserver.data.ServerContext;
import com.ij3rry.vserver.exceptions.InvalidRequestException;

public interface RequestBuilder {
    void build(ServerContext context) throws InvalidRequestException;
}

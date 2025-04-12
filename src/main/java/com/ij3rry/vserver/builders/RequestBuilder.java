package com.ij3rry.vserver.builders;

import com.ij3rry.vserver.exceptions.InvalidRequestException;
import com.ij3rry.vserver.http.data.HttpRequest;

public interface RequestBuilder {
    void build(HttpRequest httpRequest) throws InvalidRequestException;
}

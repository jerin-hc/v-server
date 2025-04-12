package com.ij3rry.vserver.http.builders;

import com.ij3rry.vserver.builders.RequestBuilder;
import com.ij3rry.vserver.exceptions.InvalidRequestException;
import com.ij3rry.vserver.http.data.HttpRequest;
import com.ij3rry.vserver.http.readers.HttpHeaderReader;

public class HttpRequestBuilder implements RequestBuilder {
    @Override
    public void build(HttpRequest request) throws InvalidRequestException {
        new HttpHeaderReader().read(request);
    }
}

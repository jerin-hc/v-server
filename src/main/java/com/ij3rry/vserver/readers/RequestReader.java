package com.ij3rry.vserver.readers;

import com.ij3rry.vserver.http.data.HttpContext;

public interface RequestReader {
    void read(HttpContext httpContext);
}

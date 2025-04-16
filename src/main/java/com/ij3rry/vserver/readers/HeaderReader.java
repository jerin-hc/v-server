package com.ij3rry.vserver.readers;

import com.ij3rry.vserver.http.data.HttpContext;

public interface HeaderReader {
    void read(HttpContext httpContext);
}

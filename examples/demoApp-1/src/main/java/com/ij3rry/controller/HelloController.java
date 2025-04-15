package com.ij3rry.controller;

import com.ij3rry.vserver.http.controller.RESTController;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.data.HttpResponse;
import com.ij3rry.vserver.http.enums.HttpResponseStatus;

public class HelloController extends RESTController {

    @Override
    public HttpResponse doGet(HttpContext context) {
        return new HttpResponse(HttpResponseStatus.OK,"Hello world");
    }
}

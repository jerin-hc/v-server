package com.ij3rry.vserver.http.controller;

import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.data.HttpResponse;
import com.ij3rry.vserver.http.enums.HttpResponseStatus;

public abstract class RESTController  {
    public HttpResponse doGet(HttpContext context){
        return new HttpResponse(HttpResponseStatus.NOT_IMPLEMENTED);
    }
    public HttpResponse doPost(HttpContext context){
        return new HttpResponse(HttpResponseStatus.NOT_IMPLEMENTED);
    }
    public HttpResponse doPut(HttpContext context){
        return new HttpResponse(HttpResponseStatus.NOT_IMPLEMENTED);
    }
    public HttpResponse doPatch(HttpContext context){
        return new HttpResponse(HttpResponseStatus.NOT_IMPLEMENTED);
    }
    public HttpResponse doDelete(HttpContext context){
        return new HttpResponse(HttpResponseStatus.NOT_IMPLEMENTED);
    }
}

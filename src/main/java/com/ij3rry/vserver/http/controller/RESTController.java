package com.ij3rry.vserver.http.controller;

import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.data.HttpResponse;

public abstract class RESTController  {
    public HttpResponse doGet(HttpContext context){
        return new HttpResponse();
    }
    public HttpResponse doPost(HttpContext context){
        return new HttpResponse();
    }
    public HttpResponse doPut(HttpContext context){
        return new HttpResponse();
    }
    public HttpResponse doPatch(HttpContext context){
        return new HttpResponse();
    }
    public HttpResponse doDelete(HttpContext context){
        return new HttpResponse();
    }
}

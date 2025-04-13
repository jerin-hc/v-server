package com.ij3rry.vserver.http.controller;

import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.data.HttpResponse;

public abstract class RESTController  {
    protected HttpResponse doGet(HttpContext context){
        return new HttpResponse();
    }
    protected HttpResponse doPost(HttpContext context){
        return new HttpResponse();
    }
}

package com.ij3rry.controller;

import com.ij3rry.dto.Employee;
import com.ij3rry.vserver.http.controller.RESTController;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.data.HttpResponse;
import com.ij3rry.vserver.http.enums.HttpResponseStatus;

public class EmployeeController extends RESTController {

    @Override
    public HttpResponse doGet(HttpContext context) {
        Employee employee = new Employee("employee" , 24, 100000);
        return new HttpResponse(HttpResponseStatus.OK, employee);
    }
}

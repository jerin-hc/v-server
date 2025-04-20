package com.ij3rry.employee.controller;

import com.ij3rry.employee.constants.EmployeeConst;
import com.ij3rry.employee.dto.Employee;
import com.ij3rry.vserver.http.controller.RESTController;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.data.HttpResponse;
import com.ij3rry.vserver.http.enums.HttpResponseStatus;

import java.util.List;
import java.util.Map;

public class EmployeeController extends RESTController {

    @Override
    public HttpResponse doGet(HttpContext context) {

        Map<String,String> pathParams =  context.getHttpRequest().getPathParams();
        final List<Employee> employees = EmployeeConst.getEmployees();
        if (pathParams.isEmpty()){
            return new HttpResponse(HttpResponseStatus.OK, employees);
        }
        String skill = pathParams.get("skill");
        String level = pathParams.get("level");
        int age = pathParams.get("age") != null ? Integer.parseInt(pathParams.get("age")) : 0;

        List<Employee> emps = employees.stream()
                .filter(employee -> employee.getSkill().equalsIgnoreCase(skill) &&
                                employee.getLevel().equalsIgnoreCase(level) &&
                                employee.getAge() >= age  ).toList();
        if(emps.isEmpty())
            return new HttpResponse(HttpResponseStatus.NO_CONTENT);

        return new HttpResponse(HttpResponseStatus.OK, emps);
    }

    @Override
    public HttpResponse doPatch(HttpContext context) {
        Map<String,String> pathVariables =  context.getHttpRequest().getPathVariable();
        int age = Integer.parseInt(pathVariables.get("age"));
        String level = pathVariables.get("level");
        EmployeeConst.getEmployees().stream()
                .filter(employee -> employee.getAge() >= age && employee.getLevel().equalsIgnoreCase(level))
                .forEach(employee -> employee.setSalary((int) (employee.getSalary() + (employee.getSalary() * 0.1))));
        return new HttpResponse(HttpResponseStatus.CREATED, EmployeeConst.getEmployees());
    }
}

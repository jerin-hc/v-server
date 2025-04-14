package com.ij3rry.TempApp.controller;

import com.ij3rry.TempApp.dto.StudentDetails;
import com.ij3rry.vserver.http.controller.RESTController;
import com.ij3rry.vserver.http.data.HttpContentType;
import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.data.HttpResponse;
import com.ij3rry.vserver.http.enums.HttpResponseStatus;

public class TempController extends RESTController {

    @Override
    public HttpResponse doPost(HttpContext context) {
        StudentDetails studentDetails = new StudentDetails();
        studentDetails.setName("jerin");
        studentDetails.setAge(29);
        return new HttpResponse(HttpResponseStatus.OK, studentDetails);
    }
}

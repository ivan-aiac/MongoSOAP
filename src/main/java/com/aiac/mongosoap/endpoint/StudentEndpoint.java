package com.aiac.mongosoap.endpoint;

import com.aiac.mongosoap.service.StudentService;
import mongosoap.aiac.com.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class StudentEndpoint {
    private static final String NAMESPACE_URI = "com.aiac.mongosoap";
    private final StudentService studentService;

    @Autowired
    public StudentEndpoint(@Qualifier("repository") StudentService studentService) {
        this.studentService = studentService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStudentByIdRequest")
    @ResponsePayload
    public StudentInfoResponse getStudentById(@RequestPayload GetStudentByIdRequest request){
        return studentService.findStudentById(request.getId());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStudentByEmailRequest")
    @ResponsePayload
    public StudentInfoResponse getStudentByEmail(@RequestPayload GetStudentByEmailRequest request){
        return studentService.findStudentByEmail(request.getEmail());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addStudentRequest")
    @ResponsePayload
    public StudentInfoResponse addStudent(@RequestPayload AddStudentRequest request){
        return studentService.addStudent(request.getStudent());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateStudentRequest")
    @ResponsePayload
    public StudentInfoResponse updateStudent(@RequestPayload UpdateStudentRequest request){
        return studentService.updateStudent(request.getStudent());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteStudentRequest")
    @ResponsePayload
    public DeleteStudentResponse deleteStudent(@RequestPayload DeleteStudentRequest request){
        return studentService.deleteStudentById(request.getId());
    }
}

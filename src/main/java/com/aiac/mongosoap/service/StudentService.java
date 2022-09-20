package com.aiac.mongosoap.service;

import mongosoap.aiac.com.DeleteStudentResponse;
import mongosoap.aiac.com.StudentInfo;
import mongosoap.aiac.com.StudentInfoResponse;

public interface StudentService {
    StudentInfoResponse findStudentById(String id);
    StudentInfoResponse findStudentByEmail(String email);
    StudentInfoResponse updateStudent(StudentInfo studentInfo);
    StudentInfoResponse addStudent(StudentInfo studentInfo);
    DeleteStudentResponse deleteStudentById(String id);
}

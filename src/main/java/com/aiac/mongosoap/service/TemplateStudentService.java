package com.aiac.mongosoap.service;

import com.aiac.mongosoap.model.Student;
import com.aiac.mongosoap.util.StudentUtils;
import com.aiac.mongosoap.exception.ServiceFault;
import com.aiac.mongosoap.exception.ServiceFaultException;
import mongosoap.aiac.com.DeleteStudentResponse;
import mongosoap.aiac.com.StudentInfo;
import mongosoap.aiac.com.StudentInfoResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@Qualifier("template")
public class TemplateStudentService implements StudentService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TemplateStudentService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public StudentInfoResponse findStudentById(String id) {
        return StudentUtils.wrapStudentInfo(findById(id));
    }

    @Override
    public StudentInfoResponse findStudentByEmail(String email) {
        if (email == null) {
            throw new ServiceFaultException(ServiceFault.MISSING_EMAIL);
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        Student student = mongoTemplate.findOne(query, Student.class);
        if (student == null) {
            throw new ServiceFaultException(ServiceFault.NOT_FOUND_EMAIL, email);
        }
        return StudentUtils.wrapStudentInfo(student);
    }

    @Override
    public StudentInfoResponse updateStudent(StudentInfo studentInfo) {
        Student student = findById(studentInfo.getId());
        BeanUtils.copyProperties(studentInfo, student, "address");
        BeanUtils.copyProperties(studentInfo.getAddress(), student.getAddress());
        student = mongoTemplate.save(student);
        return StudentUtils.wrapStudentInfo(student);
    }

    @Override
    public StudentInfoResponse addStudent(StudentInfo studentInfo) {
        if (studentInfo.getEmail() == null) {
            throw new ServiceFaultException(ServiceFault.MISSING_EMAIL);
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(studentInfo.getEmail()));
        if (mongoTemplate.exists(query, Student.class)) {
            throw new ServiceFaultException(ServiceFault.DUPLICATED_EMAIL, studentInfo.getEmail());
        }
        Student student = StudentUtils.fromStudentInfo(studentInfo);
        student = mongoTemplate.insert(student);
        return StudentUtils.wrapStudentInfo(student);
    }

    @Override
    public DeleteStudentResponse deleteStudentById(String id) {
        Student student = findById(id);
        mongoTemplate.remove(student);
        DeleteStudentResponse response = new DeleteStudentResponse();
        response.setStatus(String.format("Successfully removed student with id: %s", id));
        return response;
    }

    private void validateStudentId(String id) {
        if (id == null) {
            throw new ServiceFaultException(ServiceFault.MISSING_ID);
        }
    }

    private Student findById(String id) {
        validateStudentId(id);
        Student student = mongoTemplate.findById(id, Student.class);
        if (student == null) {
            throw new ServiceFaultException(ServiceFault.NOT_FOUND_ID, id);
        }
        return student;
    }

}

package com.aiac.mongosoap.service;

import com.aiac.mongosoap.model.Student;
import com.aiac.mongosoap.exception.ServiceFault;
import com.aiac.mongosoap.exception.ServiceFaultException;
import com.aiac.mongosoap.repository.StudentRepository;
import com.aiac.mongosoap.util.StudentUtils;
import mongosoap.aiac.com.DeleteStudentResponse;
import mongosoap.aiac.com.StudentInfo;
import mongosoap.aiac.com.StudentInfoResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("repository")
public class RepoStudentService implements StudentService {

    private final StudentRepository repository;

    @Autowired
    public RepoStudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public StudentInfoResponse findStudentById(String id) {
        validateStudentId(id);
        Student student = repository.findById(id)
                .orElseThrow(() -> new ServiceFaultException(ServiceFault.NOT_FOUND_ID, id));
        return StudentUtils.wrapStudentInfo(student);
    }

    @Override
    public StudentInfoResponse findStudentByEmail(String email) {
        validateStudentEmail(email);
        Student student = repository.findByEmail(email).
                orElseThrow(() -> new ServiceFaultException(ServiceFault.NOT_FOUND_EMAIL, email));
        return StudentUtils.wrapStudentInfo(student);
    }

    @Override
    public StudentInfoResponse updateStudent(StudentInfo studentInfo) {
        validateStudentId(studentInfo.getId());
        Optional<Student> optionalStudent = repository.findById(studentInfo.getId());
        if (optionalStudent.isEmpty()) {
            throw new ServiceFaultException(ServiceFault.NOT_FOUND_ID, studentInfo.getId());
        }
        Student student = optionalStudent.get();
        BeanUtils.copyProperties(studentInfo, student, "address");

        BeanUtils.copyProperties(studentInfo.getAddress(), student.getAddress());
        student = repository.save(student);
        return StudentUtils.wrapStudentInfo(student);
    }

    @Override
    public StudentInfoResponse addStudent(StudentInfo studentInfo) {
        validateStudentEmail(studentInfo.getEmail());
        if (repository.existsByEmail(studentInfo.getEmail())) {
            throw new ServiceFaultException(ServiceFault.DUPLICATED_EMAIL, studentInfo.getEmail());
        }
        Student student = StudentUtils.fromStudentInfo(studentInfo);
        return StudentUtils.wrapStudentInfo(repository.insert(student));
    }

    @Override
    public DeleteStudentResponse deleteStudentById(String id) {
        validateStudentId(id);
        Optional<Student> optionalStudent = repository.findById(id);
        if (optionalStudent.isEmpty()) {
            throw new ServiceFaultException(ServiceFault.NOT_FOUND_ID, id);
        }
        Student student = optionalStudent.get();
        repository.delete(student);
        DeleteStudentResponse response = new DeleteStudentResponse();
        response.setStatus(String.format("Successfully removed student with id: %s", id));
        return response;
    }

    private void validateStudentId(String id) {
        if (id == null) {
            throw new ServiceFaultException(ServiceFault.MISSING_ID);
        }
    }

    private void validateStudentEmail(String email) {
        if (email == null) {
            throw new ServiceFaultException(ServiceFault.MISSING_EMAIL);
        }
    }
}

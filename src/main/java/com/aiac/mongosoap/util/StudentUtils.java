package com.aiac.mongosoap.util;

import com.aiac.mongosoap.model.Address;
import com.aiac.mongosoap.model.Student;
import mongosoap.aiac.com.AddressInfo;
import mongosoap.aiac.com.StudentInfo;
import mongosoap.aiac.com.StudentInfoResponse;
import org.springframework.beans.BeanUtils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.List;

public class StudentUtils {

    public static Student fromStudentInfo(StudentInfo studentInfo) {
        Address address = new Address();
        BeanUtils.copyProperties(studentInfo.getAddress(), address);
        Student student = new Student();
        BeanUtils.copyProperties(studentInfo, student, "address", "created", "favoriteSubjects");
        student.setFavoriteSubjects(List.copyOf(studentInfo.getFavoriteSubjects()));
        student.setAddress(address);
        if (studentInfo.getCreated() != null) {
            student.setCreated(studentInfo.getCreated().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
        } else {
            student.setCreated(LocalDateTime.now());
        }
        return student;
    }

    public static StudentInfoResponse wrapStudentInfo(Student student) {
        AddressInfo addressInfo = new AddressInfo();
        BeanUtils.copyProperties(student.getAddress(), addressInfo);
        StudentInfo studentInfo = new StudentInfo();
        BeanUtils.copyProperties(student, studentInfo, "address", "created", "favoriteSubjects");
        studentInfo.getFavoriteSubjects().addAll(student.getFavoriteSubjects());
        studentInfo.setAddress(addressInfo);
        XMLGregorianCalendar xmlCreated = DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(student.getCreated().toString());
        studentInfo.setCreated(xmlCreated);
        StudentInfoResponse response = new StudentInfoResponse();
        response.setStudent(studentInfo);
        return response;
    }
}

package com.aiac.mongosoap.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mongosoap.aiac.com.Gender;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@NoArgsConstructor
public class Student {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Gender gender;
    @Indexed(unique = true)
    private String email;
    private Address address;
    private List<String> favoriteSubjects;
    private float gpa;
    private LocalDateTime created;

    public Student(String firstName, String lastName, Gender gender, String email, Address address, List<String> favoriteSubjects, float gpa) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.favoriteSubjects = favoriteSubjects;
        this.gpa = gpa;
        this.created = LocalDateTime.now();
    }
}

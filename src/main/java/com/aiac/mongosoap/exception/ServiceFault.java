package com.aiac.mongosoap.exception;

public enum ServiceFault {
    MISSING_ID("Required ID","Student's ID is required for this operation"),
    MISSING_EMAIL("Required Email","Student's email is required for this operation"),
    DUPLICATED_EMAIL("Already Registered", "Email %s is already registered"),
    NOT_FOUND_ID("Student not found", "Student with ID: %s was not found"),
    NOT_FOUND_EMAIL("Student not found", "Student with email: %s was not found");

    private final String cause;
    private final String message;

    ServiceFault(String cause, String message) {
        this.cause = cause;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCause() {
        return cause;
    }
}

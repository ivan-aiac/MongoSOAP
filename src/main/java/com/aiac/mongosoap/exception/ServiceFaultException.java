package com.aiac.mongosoap.exception;


public class ServiceFaultException extends RuntimeException{
    private final String statusCode;
    private final String description;

    public ServiceFaultException(String cause, String statusCode, String description) {
        super(cause);
        this.statusCode = statusCode;
        this.description = description;
    }

    public ServiceFaultException(ServiceFault serviceFault) {
        this(serviceFault.getCause(), serviceFault.name(), serviceFault.getMessage());
    }

    public ServiceFaultException(ServiceFault serviceFault, String detail) {
        this(serviceFault.getCause(), serviceFault.name(), String.format(serviceFault.getMessage(), detail));
    }


    public String getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return description;
    }
}

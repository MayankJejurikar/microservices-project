package com.enrollmentservice.enrollmentservice.exception;

public class AlreadyEnrolledException extends RuntimeException {

    public AlreadyEnrolledException(String message) {
        super(message);
    }
}

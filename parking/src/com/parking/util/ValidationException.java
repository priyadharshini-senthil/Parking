package com.parking.util;


public class ValidationException extends Exception {

    private String message;

    public ValidationException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

package com.parking.util;

public class FineProcessingException extends Exception {

    private String message;

    public FineProcessingException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

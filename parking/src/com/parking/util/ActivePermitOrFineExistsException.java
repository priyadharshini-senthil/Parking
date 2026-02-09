package com.parking.util;

public class ActivePermitOrFineExistsException extends Exception {

    private String message;

    public ActivePermitOrFineExistsException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

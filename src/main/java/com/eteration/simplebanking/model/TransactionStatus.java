package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation

public class TransactionStatus {
    private String status;
    private String message;

    public TransactionStatus(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

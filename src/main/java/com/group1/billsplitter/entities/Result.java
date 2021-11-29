package com.group1.billsplitter.entities;

import org.springframework.stereotype.Component;

@Component
public class Result {
    private boolean successful;
    private String message;

    public Result() {

    }
    public Result(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

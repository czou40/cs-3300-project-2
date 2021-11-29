package com.group1.billsplitter.entities;

import org.springframework.stereotype.Component;

@Component
public class Result<T> {
    private boolean successful;
    private String message;
    private T payload;

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public Result(boolean successful, String message, T payload) {
        this.successful = successful;
        this.message = message;
        this.payload = payload;
    }

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

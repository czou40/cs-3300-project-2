package com.group1.billsplitter.entities.requests;

import org.springframework.stereotype.Component;

@Component
public class Message {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }
}

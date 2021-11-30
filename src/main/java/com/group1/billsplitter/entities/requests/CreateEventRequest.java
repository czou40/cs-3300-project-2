package com.group1.billsplitter.entities.requests;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Component
public class CreateEventRequest {
    @NotNull
    @NotEmpty
    private String eventName;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public CreateEventRequest() {

    }

    public CreateEventRequest(String eventName) {
        this.eventName = eventName;
    }
}

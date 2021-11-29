package com.group1.billsplitter.entities.requests;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Component
public class SetPaypalEmailRequest {
    @NotNull
    @Email
    private String paypalEmail;


    public SetPaypalEmailRequest() {
    }


    public SetPaypalEmailRequest(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public void setPaypalEmail(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }
}

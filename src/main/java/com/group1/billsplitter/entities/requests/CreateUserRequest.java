package com.group1.billsplitter.entities.requests;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Component
public class CreateUserRequest {
    @NotNull
    @Email
    private String email;
    @NotNull
    @Length(min = 6)
    private String password;
    @NotNull
    private String name;
    @NotNull
    @Email
    private String paypalEmail;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public CreateUserRequest(String email, String password, String name, String paypalEmail) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.paypalEmail = paypalEmail;
    }

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public void setPaypalEmail(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

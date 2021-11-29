package com.group1.billsplitter.entities;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class User {
    private String email;
    private String name;
    private String paypalEmail;

    public User() {

    }

    public User(String email, String name, String paypalEmail) {
        this.email = email;
        this.name = name;
        this.paypalEmail = paypalEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public void setPaypalEmail(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", paypalEmail='" + paypalEmail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email) && name.equals(user.name) && paypalEmail.equals(user.paypalEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, paypalEmail);
    }
}

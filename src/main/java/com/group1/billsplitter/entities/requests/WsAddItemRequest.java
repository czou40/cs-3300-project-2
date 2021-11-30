package com.group1.billsplitter.entities.requests;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnegative;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Component
public class WsAddItemRequest {
    @NotNull
    @NotEmpty
    private String token;
    @NotNull
    @NotEmpty
    private String eventId;
    @NotNull
    @Nonnegative
    @NotEmpty
    private long price;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @Positive
    @NotEmpty
    private int quantity;

    public double getPrice() {
        return this.price * 1.0 / 100;
    }

    public void setPrice(double price) {
        this.price = Math.round(price * 100);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    public WsAddItemRequest() {

    }
    public WsAddItemRequest(String token, String eventId,double price, String name, int quantity) {
        this.token = token;
        this.eventId = eventId;
        this.price = Math.round(price * 100);
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "WsAddItemRequest{" +
                "token='" + token + '\'' +
                ", eventId='" + eventId + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

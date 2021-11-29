package com.group1.billsplitter.entities.requests;

import com.group1.billsplitter.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnegative;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Component
public class AddItemRequest {
    @NotNull
    @Nonnegative
    private long price;
    @NotNull
    private String name;
    @NotNull
    @Positive
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

    public AddItemRequest() {
    }

    public AddItemRequest(double price, String name, int quantity) {
        this.price = Math.round(price * 100);
        this.name = name;
        this.quantity = quantity;
    }
}

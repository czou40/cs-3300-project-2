package com.group1.billsplitter.entities;

import org.springframework.stereotype.Component;

import javax.annotation.Nonnegative;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;
import java.util.UUID;

@Component
public class Item {
    @NotNull
    @Nonnegative
    private long price;
    @NotNull
    private String name;
    @NotNull
    @Positive
    private int quantity;
    @NotNull
    private User orderer;
    private String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Item(String name, double price, int quantity, User orderer) {
        this.price=Math.round(price * 100);
        this.name = name;
        this.quantity = quantity;
        this.orderer = orderer;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Item{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", orderer=" + orderer +
                ", id='" + id + '\'' +
                '}';
    }

    public Item() {
    }

    public User getOrderer() {
        return orderer;
    }

    public void setOrderer(User orderer) {
        this.orderer = orderer;
    }

    public double getPrice() {
        return price * 1.0 / 100;
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

    public String getId() {
        return id;
    }
}

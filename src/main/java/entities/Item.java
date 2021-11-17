package entities;

import org.springframework.stereotype.Component;

@Component
public class Item {
    private double price;
    private String name;
    private int quantity;

    public Item(int price, String name, int quantity) {
        this.price = price;
        this.name = name;
        this.quantity = quantity;
    }

    public Item() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
}

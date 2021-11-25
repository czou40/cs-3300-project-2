package entities;

import org.springframework.stereotype.Component;

@Component
public class Account {
    private String username;
    private String paymentMethod;
    private String details;

    public Account(String username, String paymentMethod, String details) {
        this.username = username;
        this.paymentMethod = paymentMethod;
        this.details = details;
    }

    public Account() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

package com.group1.billsplitter.entities;

import org.springframework.stereotype.Component;

@Component
public class Bill {
    private Integer id;
    private String lender;
    private String borrower;
    private Double amount;
    private String note;

    public Bill(Integer id, String lender, String borrower, Double amount, String note) {
        this.id = id;
        this.lender = lender;
        this.borrower = borrower;
        this.amount = amount;
        this.note = note;
    }

    public Bill() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

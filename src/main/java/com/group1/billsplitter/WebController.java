package com.group1.billsplitter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.*;

import entities.Account;
import entities.Bill;
import entities.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    private final JdbcTemplate jdbcTemplate;

    public WebController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // User handlers
    @GetMapping("/getUser/{username}")
    public User getUser(@PathVariable String username) {
        List<Map<String, Object>> userList = this.jdbcTemplate.queryForList("SELECT * FROM users WHERE username = ?", username);
        if (userList.size() == 0) {
            return new User("", "");
        }
        Map<String, Object> user = userList.get(0);
        return new User((String) user.get("username"), (String) user.get("name"));
    }

    @PostMapping("/createUser")
    public void createUser(@RequestBody User newUser) {
        jdbcTemplate.update(
                "INSERT INTO users (username, name) VALUES (?, ?)",
                newUser.getUsername(), newUser.getName()
        );
    }

    @PutMapping("/updateUser")
    public void updateUser(@RequestBody User newUser) {
        jdbcTemplate.update(
                "UPDATE users SET name = ? WHERE username = ?",
                newUser.getName(), newUser.getUsername()
        );
    }

    @DeleteMapping("/deleteUser/{username}")
    public void deleteUser(@PathVariable String username) {
        jdbcTemplate.update(
                "DELETE FROM users WHERE username = ?",
                username
        );
    }

    // Account handlers
    @GetMapping("/getAccounts/{username}")
    public List<Account> getAccounts(@PathVariable String username) {
        return this.jdbcTemplate.queryForList("SELECT * FROM accounts WHERE username = ?", username).stream().map(obj -> new Account((String) obj.get("username"), (String) obj.get("paymentMethod"), (String) obj.get("details"))).collect(Collectors.toList());
    }

    @PostMapping("/addAccount")
    public void addAccount(@RequestBody Account newAccount) {
        jdbcTemplate.update(
                "INSERT INTO accounts (username, paymentMethod, details) VALUES (?, ?, ?)",
                newAccount.getUsername(), newAccount.getPaymentMethod(), newAccount.getDetails()
        );
    }

    @PutMapping("/updateAccount")
    public void updateAccount(@RequestBody Account newAccount) {
        jdbcTemplate.update(
                "UPDATE accounts SET details = ? WHERE username = ? AND paymentMethod = ?",
                newAccount.getDetails(), newAccount.getUsername(), newAccount.getPaymentMethod()
        );
    }

    @DeleteMapping("/deleteAccount")
    public void deleteAccount(@RequestBody Account account) {
        jdbcTemplate.update(
                "DELETE FROM accounts WHERE username = ? AND paymentMethod = ?",
                account.getUsername(), account.getPaymentMethod()
        );
    }

    // Bill handlers
    @GetMapping("/getBorrows/{username}")
    public List<Bill> getBorrows(@PathVariable String username) {
        return this.jdbcTemplate.queryForList("SELECT * FROM bills WHERE borrower = ?", username).stream().map(obj -> new Bill((Integer) obj.get("id"), (String) obj.get("lender"), (String) obj.get("borrower"), (Double) obj.get("amount"), (String) obj.get("note"))).collect(Collectors.toList());
    }

    @GetMapping("/getLends/{username}")
    public List<Bill> getLends(@PathVariable String username) {
        return this.jdbcTemplate.queryForList("SELECT * FROM bills WHERE lender = ?", username).stream().map(obj -> new Bill((Integer) obj.get("id"), (String) obj.get("lender"), (String) obj.get("borrower"), (Double) obj.get("amount"), (String) obj.get("note"))).collect(Collectors.toList());
    }

    @PostMapping("/addBill")
    public void addBill(@RequestBody Bill newBill) {
        jdbcTemplate.update(
                "INSERT INTO bills (borrower, lender, amount, note) VALUES (?, ?, ?, ?)",
                newBill.getBorrower(), newBill.getLender(), newBill.getAmount(), newBill.getNote()
        );
    }

    @PutMapping("/updateBill")
    public void updateBill(@RequestBody Bill newBill) {
        jdbcTemplate.update(
                "UPDATE bills SET borrower = ?, lender = ?, amount = ?, note = ? WHERE id = ?",
                newBill.getBorrower(), newBill.getLender(), newBill.getAmount(), newBill.getNote(), newBill.getId()
        );
    }

    @DeleteMapping("/deleteBill/{id}")
    public void deleteBill(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM bills WHERE id = ?",
                id
        );
    }
}
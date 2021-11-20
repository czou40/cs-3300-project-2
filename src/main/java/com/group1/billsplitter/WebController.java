package com.group1.billsplitter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.*;

import entities.Account;
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
    public List<Map<String, Object>> getAccounts(@PathVariable String username) {
        return this.jdbcTemplate.queryForList("SELECT * FROM accounts WHERE username = ?", username);
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
}
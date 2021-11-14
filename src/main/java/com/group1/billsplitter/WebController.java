package com.group1.billsplitter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    private final JdbcTemplate jdbcTemplate;

    public WebController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/getUsers")
    public List<String> getUsers() {
        return this.jdbcTemplate.queryForList("SELECT * FROM user").stream()
                .map(m -> m.values().toString())
                .collect(Collectors.toList());
    }
}
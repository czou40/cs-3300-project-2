package com.group1.billsplitter.controllers;

import entities.Item;
import entities.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    @MessageMapping("/test")
    @SendTo("/topic/test")
    public Item broadcastUpdate(@RequestBody Item item) {
        System.out.println(item.getName());
        return item;
    }
}

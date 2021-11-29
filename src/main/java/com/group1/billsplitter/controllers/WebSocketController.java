package com.group1.billsplitter.controllers;

import com.group1.billsplitter.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableScheduling
public class WebSocketController {
    private List<Item> temp = new ArrayList<>();
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/test")
    @SendTo("/topic/test")
    public Item broadcastUpdate(@RequestBody Item item) {
        temp.add(item);
        System.out.println(item.getName());
        return item;
    }

    @SubscribeMapping("/topic/test")
    @SendToUser("/queue/test")
    public List<Item> sendInitialData() {
        return temp;
    }
}

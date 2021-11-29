package com.group1.billsplitter.controllers;

import com.group1.billsplitter.entities.Event;
import com.group1.billsplitter.entities.Item;
import com.group1.billsplitter.entities.Result;
import com.group1.billsplitter.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableScheduling
public class WebSocketController {
    private SimpMessagingTemplate messagingTemplate;
    private FirebaseService firebaseService;

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Autowired
    public void setFirebaseService(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @MessageMapping("/events")
    @SendTo("/topic/events")
    public Item broadcastUpdate(@RequestBody Item item, @PathVariable String eventId) {
        temp.add(item);
        System.out.println(item.getName());
        return item;
    }

    @MessageMapping("/topic/events")
    @SendToUser("/queue/events")
    public ResponseEntity<Object> sendInitialData(@RequestBody Item item) {
        Result result = firebaseService.getEvent(eventId);
        if (result.isSuccessful()) {
            return
        }
        return
    }
}

package com.group1.billsplitter.controllers;

import com.google.firebase.auth.FirebaseToken;
import com.group1.billsplitter.entities.Event;
import com.group1.billsplitter.entities.Item;
import com.group1.billsplitter.entities.Result;
import com.group1.billsplitter.entities.User;
import com.group1.billsplitter.entities.requests.Message;
import com.group1.billsplitter.entities.requests.WsDeleteItemRequest;
import com.group1.billsplitter.entities.requests.WsJoinRequest;
import com.group1.billsplitter.entities.requests.WsAddItemRequest;
import com.group1.billsplitter.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@EnableScheduling
public class WebSocketController {
    private SimpMessagingTemplate messagingTemplate;
    private FirebaseService firebaseService;
//    private UserController userController;
//
//    @Autowired
//    public void setUserController(UserController userController) {
//        this.userController = userController;
//    }

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Autowired
    public void setFirebaseService(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @MessageMapping("/events/deleteItem")
//    @SendTo("/topic/events")
    public void broadcastUpdate(@RequestBody WsDeleteItemRequest request) {
        try {
            FirebaseToken verified = firebaseService.verifyIdToken(request.getToken());
            Result<Event> result = firebaseService.deleteItem(request.getEventId(), request.getItemId());
            if (!result.isSuccessful()) {
                System.out.println(result.getMessage());
                return;
            }
            Event event = result.getPayload();
            messagingTemplate.convertAndSend("/topic/events/"+event.getId(), event);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    @MessageMapping("/events")
    public void broadcastUpdate(@RequestBody WsAddItemRequest request) {
        System.out.println(request);
        try {
            FirebaseToken verified = firebaseService.verifyIdToken(request.getToken());
            User user = firebaseService.getUserByUid(verified.getUid()).getPayload();
            Item item = new Item(request.getName(), request.getPrice(), request.getQuantity(), user);
            Result<Event> result = firebaseService.addItem(request.getEventId(), item);
            if (!result.isSuccessful()) {
                System.out.println("err1");
                System.out.println(result.getMessage());
                return;
            }
            Event event = result.getPayload();
            messagingTemplate.convertAndSend("/topic/events/"+event.getId(), event);
        } catch (Exception e) {
            System.out.println("err2");
            System.out.println(e.getMessage());
            return;
        }
    }

    @MessageMapping("/events/join")
    public void joinEvent(@RequestBody WsJoinRequest request) {
        try {
            firebaseService.verifyIdToken(request.getToken());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        Result<Event> result = firebaseService.getEvent(request.getEventId().trim());
        if (!result.isSuccessful()) {
            System.out.println(result.getMessage());
            return;
        }
        Event event = result.getPayload();
        messagingTemplate.convertAndSend("/topic/events/"+event.getId(), event);
//        return result.isSuccessful() ? result.getPayload() : null;
    }

}

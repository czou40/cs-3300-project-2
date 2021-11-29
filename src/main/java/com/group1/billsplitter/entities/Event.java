package com.group1.billsplitter.entities;

import com.google.api.gax.rpc.InvalidArgumentException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.constraints.NotNull;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Event {
    @NotNull
    private String eventName;
    private String id;
    @NotNull
    private User payer;
    private List<User> attendees;
    private List<Item> items;

    public Event() {
    }

    public Event(String eventName, User payer) {
        this.eventName = eventName;
        this.payer = payer;
        this.attendees = new ArrayList<>();
        this.attendees.add(payer);
        this.items = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventName='" + eventName + '\'' +
                ", id='" + id + '\'' +
                ", payer=" + payer +
                ", attendees=" + attendees +
                ", items=" + items +
                '}';
    }

    public void setAttendees(List<User> attendees) {
        this.attendees = attendees;
    }

    public void addAttendee(User attendee) {
        if (attendees.contains(attendee)) {
            return;
        }
        this.attendees.add(attendee);
    }

    public void removeAttendee(User attendee) {
        if (attendee.equals(payer)) {
            if (attendees.size() == 1) {
                throw new InvalidParameterException("There must be at least one attendee!");
            }
            this.attendees.remove(attendee);
            this.payer = attendees.get(0);
        } else {
            this.attendees.remove(attendee);
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        if (items.contains(item)) {
            return;
        }
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public void removeItem(String itemId) {
        this.items.removeIf(item -> {
            return item.getId().equals(itemId);
        });
    }
}

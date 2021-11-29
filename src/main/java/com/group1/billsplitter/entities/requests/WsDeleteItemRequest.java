package com.group1.billsplitter.entities.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class WsDeleteItemRequest {
    private String token;
    private String eventId;
    private String itemId;
}

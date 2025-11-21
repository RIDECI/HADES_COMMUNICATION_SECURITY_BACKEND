package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events;



import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversationCreatedEvent {

    private String conversationId;

    private Long tripId; 

    private List<String> participants;

    private String type; 

    private LocalDateTime createdAt;
    
}

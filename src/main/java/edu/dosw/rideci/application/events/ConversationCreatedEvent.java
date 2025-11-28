package edu.dosw.rideci.application.events;

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

    private Long travelId;

    private List<Long> participants;

    private String type;

    private LocalDateTime createdAt;

}

package edu.dosw.rideci.application.events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageSentEvent {

    private String conversationId;

    private String messageId;

    private String senderId;

    private String content;

    private LocalDateTime sentAt;
}
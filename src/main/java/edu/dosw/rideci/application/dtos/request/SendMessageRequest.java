package edu.dosw.rideci.application.dtos.request;

import lombok.Data;

/**
 * DTO para el envío de mensajes en los chats.
 */
@Data
public class SendMessageRequest {
    private String senderId;
    private String content;
}

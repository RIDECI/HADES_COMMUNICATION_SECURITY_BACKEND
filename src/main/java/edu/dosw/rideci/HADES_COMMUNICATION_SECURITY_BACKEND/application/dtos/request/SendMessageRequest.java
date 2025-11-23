package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.request;

import lombok.Data;

/**
 * DTO para el envío de mensajes en los chats.
 */
@Data
public class SendMessageRequest {
    private String conversationId;
    private String senderId;
    private String content;
}


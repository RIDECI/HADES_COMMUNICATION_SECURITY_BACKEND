package edu.dosw.rideci.domain.entities;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Clase entidad para el manejo de la información de mensaje.
 */
@Data
@Document(collection = "messages")
public class Message {

    @Id
    private final String messageId;
    private final String conversationId;
    private final String senderId;
    private final String content;
    private final Date timestamp;

    public Message(String conversationId, String senderId, String content) {
        this.messageId = UUID.randomUUID().toString();
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.content = content;
        this.timestamp = new Date();
    }
}

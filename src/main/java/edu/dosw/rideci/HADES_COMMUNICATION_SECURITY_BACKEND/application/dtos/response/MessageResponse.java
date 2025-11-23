package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que devuelve información sobre el envío de un mensaje.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private String messageId;
    private String conversationId;
    private String senderId;
    private String content;
    private Date timestamp;
}


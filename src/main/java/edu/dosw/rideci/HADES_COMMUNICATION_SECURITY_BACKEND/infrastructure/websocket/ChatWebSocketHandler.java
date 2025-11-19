package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response.MessageResponse;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.service.ConversationService;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.Message;
import lombok.Data;


/**
 * Clase WebSocket para comunicación en tiempo real en chats. Este componente permite la comunicación bidireccional en tiempo real entre
 * conductores y pasajeros, facilitando la mensajería instantánea durante los viajes.
 */
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ConversationService conversationService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, List<WebSocketSession>> activeSessions = new HashMap<>();

    public ChatWebSocketHandler(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Nueva conexión WebSocket: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMsg) throws Exception {

        IncomingMessage incoming = mapper.readValue(textMsg.getPayload(), IncomingMessage.class);

        String conversationId = incoming.getConversationId();
        activeSessions.putIfAbsent(conversationId, new ArrayList<>());
        if (!activeSessions.get(conversationId).contains(session)) {
            activeSessions.get(conversationId).add(session);
        }

        Message message = new Message(
                conversationId,
                incoming.getSenderId(),
                incoming.getContent()
        );


        conversationService.sendMessage(conversationId, message);

        MessageResponse savedMsg = conversationService.getMessages(conversationId)
                                        .stream()
                                        .filter(m -> m.getSenderId().equals(message.getSenderId()) 
                                                  && m.getContent().equals(message.getContent()))
                                        .findFirst()
                                        .orElseThrow(() -> new RuntimeException("Error al enviar mensaje"));

        String json = mapper.writeValueAsString(savedMsg);

        for (WebSocketSession s : activeSessions.get(conversationId)) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(json));
            }
        }
    }

    @Data
    private static class IncomingMessage {
        private String conversationId;
        private String senderId;
        private String content;
    }
}

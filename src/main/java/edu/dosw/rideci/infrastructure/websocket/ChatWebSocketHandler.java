package edu.dosw.rideci.infrastructure.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.dosw.rideci.application.dtos.response.MessageResponse;
import edu.dosw.rideci.application.events.MessageSentEvent;
import edu.dosw.rideci.application.service.ConversationService;
import edu.dosw.rideci.domain.entities.Message;
import lombok.Data;

/**
 * Clase WebSocket para comunicación en tiempo real en chats. Este componente
 * permite la comunicación bidireccional en tiempo real entre
 * conductores y pasajeros, facilitando la mensajería instantánea durante los
 * viajes.
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
                incoming.getContent());

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

    public void sendMessageToConversation(String conversationId, MessageSentEvent event) {
        try {
            String json = mapper.writeValueAsString(event);

            if (!activeSessions.containsKey(conversationId)) {
                System.out.println("⚠️ No hay sesiones activas para conversacion " + conversationId);
                return;
            }

            for (WebSocketSession session : activeSessions.get(conversationId)) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(json));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error enviando mensaje por WebSocket", e);
        }
    }

    @Data
    private static class IncomingMessage {
        private String conversationId;
        private String senderId;
        private String content;
    }
}

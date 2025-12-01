package edu.dosw.rideci.infrastructure.websocket;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.dosw.rideci.application.dtos.response.MessageResponse;
import edu.dosw.rideci.domain.enums.TravelType;

@Component
public class ChatWebSocketSender {

    private final WebSocketSessionManager sessionManager;
    private final ObjectMapper mapper = new ObjectMapper();

    public ChatWebSocketSender(WebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void sendMessage(MessageResponse msg, List<Long> participantIds, TravelType travelType) {
        try {
            String json = mapper.writeValueAsString(msg);

            if (travelType == TravelType.TRIP) {
                if (msg.getReceiverId() != null && !msg.getReceiverId().isEmpty()) {
                    List<WebSocketSession> sessions = sessionManager.getSessions(msg.getReceiverId());
                    if (sessions != null) {
                        for (WebSocketSession s : sessions) {
                            if (s.isOpen()) s.sendMessage(new TextMessage(json));
                        }
                    }
                }
            } else if (travelType == TravelType.GROUP) {
                for (Long userId : participantIds) {
                    if (userId.toString().equals(msg.getSenderId())) continue;
                    List<WebSocketSession> sessions = sessionManager.getSessions(userId.toString());
                    if (sessions != null) {
                        for (WebSocketSession s : sessions) {
                            if (s.isOpen()) s.sendMessage(new TextMessage(json));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error enviando mensaje a conversación " + msg.getConversationId() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}

package edu.dosw.rideci.infrastructure.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.dosw.rideci.application.dtos.response.MessageResponse;

@Component
public class ChatWebSocketSender {

    private final WebSocketSessionManager sessionManager;
    private final ObjectMapper mapper = new ObjectMapper();

    public ChatWebSocketSender(WebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void sendToUser(String userId, MessageResponse msg) {
        try {
            String json = mapper.writeValueAsString(msg);

            var sessions = sessionManager.getSessions(userId);
            if (sessions != null && !sessions.isEmpty()) {
                for (WebSocketSession s : sessions) {
                    if (s.isOpen()) {
                        s.sendMessage(new TextMessage(json));
                    }
                }
            } else {
                System.out.println("No hay sesiones activas para el usuario: " + userId);
            }
        } catch (Exception e) {
            System.err.println("Error enviando mensaje a usuario " + userId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}

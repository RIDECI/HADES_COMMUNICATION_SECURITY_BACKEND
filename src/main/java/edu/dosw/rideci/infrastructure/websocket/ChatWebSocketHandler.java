package edu.dosw.rideci.infrastructure.websocket;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.dosw.rideci.application.dtos.response.MessageResponse;
import edu.dosw.rideci.application.exceptions.ConversationException;
import edu.dosw.rideci.application.service.ConversationService;
import edu.dosw.rideci.domain.entities.Message;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager sessionManager;
    private final ConversationService conversationService;
    private final ObjectMapper mapper = new ObjectMapper();

    public ChatWebSocketHandler(WebSocketSessionManager sessionManager,
                                ConversationService conversationService) {
        this.sessionManager = sessionManager;
        this.conversationService = conversationService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = (String) session.getAttributes().get("userId");
        sessionManager.addSession(userId, session);
        System.out.println("WebSocket conectado: " + userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMsg) {
        try {
            IncomingMessage incoming = mapper.readValue(textMsg.getPayload(), IncomingMessage.class);
            var convResponse = conversationService.getConversation(incoming.getConversationId());
            List<Long> participants = convResponse.getParticipants();

            if (convResponse.getType().name().equals("TRIP") && incoming.getReceiverId() != null) {
                Message message = new Message(
                        incoming.getConversationId(),
                        incoming.getReceiverId(),
                        incoming.getSenderId(),
                        incoming.getContent()
                );
                conversationService.sendMessage(incoming.getConversationId(), message);
                MessageResponse response = conversationService.toMessageResponse(message);
                String json = mapper.writeValueAsString(response);
                List<WebSocketSession> sessions = sessionManager.getSessions(incoming.getReceiverId());
                if (sessions != null) {
                    for (WebSocketSession s : sessions) {
                        if (s.isOpen()) {
                            s.sendMessage(new TextMessage(json));
                        }
                    }
                }
                return;
            }

            for (Long participantId : participants) {
                if (participantId.toString().equals(incoming.getSenderId())) continue;

                Message message = new Message(
                        incoming.getConversationId(),
                        participantId.toString(),
                        incoming.getSenderId(),
                        incoming.getContent()
                );

                conversationService.sendMessage(incoming.getConversationId(), message);
                MessageResponse response = conversationService.toMessageResponse(message);
                String json = mapper.writeValueAsString(response);
                List<WebSocketSession> sessions = sessionManager.getSessions(participantId.toString());
                if (sessions != null) {
                    for (WebSocketSession s : sessions) {
                        if (s.isOpen()) {
                            s.sendMessage(new TextMessage(json));
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = (String) session.getAttributes().get("userId");
        sessionManager.removeSession(userId, session);
    }

    private static class IncomingMessage {
        private String conversationId;
        private String senderId;
        private String receiverId;
        private String content;

        public String getConversationId() { return conversationId; }
        public void setConversationId(String conversationId) { this.conversationId = conversationId; }
        public String getSenderId() { return senderId; }
        public void setSenderId(String senderId) { this.senderId = senderId; }
        public String getReceiverId() { return receiverId; }
        public void setReceiverId(String receiverId) { this.receiverId = receiverId; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
}

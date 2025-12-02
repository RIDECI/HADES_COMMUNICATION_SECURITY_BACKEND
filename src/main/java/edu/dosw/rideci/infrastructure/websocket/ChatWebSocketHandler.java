package edu.dosw.rideci.infrastructure.websocket;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.dosw.rideci.application.dtos.response.MessageResponse;
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
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            sessionManager.addSession(userId, session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMsg) throws Exception {
        IncomingMessage incoming = mapper.readValue(textMsg.getPayload(), IncomingMessage.class);
        if (incoming.getConversationId() == null || incoming.getSenderId() == null || incoming.getContent() == null) return;

        var convResponse = conversationService.getConversation(incoming.getConversationId());
        List<Long> participants = convResponse.getParticipants();

        if ("TRIP".equals(convResponse.getType())) {
            if (incoming.getReceiverId() != null && !incoming.getReceiverId().isEmpty()) {
                sendMessageToUser(incoming.getConversationId(), incoming.getSenderId(), incoming.getReceiverId(), incoming.getContent());
            }
        } else if ("GROUP".equals(convResponse.getType())) {
            for (Long participantId : participants) {
                if (participantId.toString().equals(incoming.getSenderId())) continue;
                sendMessageToUser(incoming.getConversationId(), incoming.getSenderId(), participantId.toString(), incoming.getContent());
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) sessionManager.removeSession(userId, session);
    }

    private void sendMessageToUser(String conversationId, String senderId, String receiverId, String content) throws Exception {
        Message message = new Message(conversationId, receiverId, senderId, content);
        conversationService.sendMessage(conversationId, message);
        MessageResponse response = conversationService.toMessageResponse(message);
        String json = mapper.writeValueAsString(response);
        List<WebSocketSession> sessions = sessionManager.getSessions(receiverId);
        if (sessions != null) {
            for (WebSocketSession s : sessions) {
                if (s.isOpen()) s.sendMessage(new TextMessage(json));
            }
        }
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

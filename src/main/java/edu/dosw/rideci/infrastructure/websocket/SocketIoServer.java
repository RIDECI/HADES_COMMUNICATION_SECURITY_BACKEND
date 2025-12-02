package edu.dosw.rideci.infrastructure.websocket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class SocketIoServer {

    private SocketIOServer server;

    @PostConstruct
    public void start() {
        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(3050);

        server = new SocketIOServer(config);

        server.addEventListener("message", Message.class, (client, data, ackSender) -> {
            System.out.println("Mensaje recibido: " + data.getContent());
            server.getRoomOperations(data.getConversationId()).sendEvent("message", data);
        });

        server.start();
        System.out.println("Socket.IO server iniciado en puerto 3050");
    }

    @PreDestroy
    public void stop() {
        server.stop();
    }

    public static class Message {
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

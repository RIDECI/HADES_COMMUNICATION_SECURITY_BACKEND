package edu.dosw.rideci.infrastructure.websocket;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketSessionManager {

    private final ConcurrentHashMap<String, List<WebSocketSession>> sessions = new ConcurrentHashMap<>();

    public void addSession(String userId, WebSocketSession session) {
        sessions.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(session);
    }

    public void removeSession(String userId, WebSocketSession session) {
        List<WebSocketSession> list = sessions.get(userId);
        if (list != null) {
            list.remove(session);
            if (list.isEmpty()) {
                sessions.remove(userId);
            }
        }
    }

    public List<WebSocketSession> getSessions(String userId) {
        return sessions.get(userId);
    }
}

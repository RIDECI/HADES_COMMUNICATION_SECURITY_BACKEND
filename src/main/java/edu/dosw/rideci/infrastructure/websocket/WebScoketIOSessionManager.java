package edu.dosw.rideci.infrastructure.websocket;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;

@Component
public class WebScoketIOSessionManager {
    private final ConcurrentHashMap<String, List<SocketIOClient>> sessions = new ConcurrentHashMap<>();

    public void addSession(String userId, SocketIOClient client) {
        sessions.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(client);
    }

    public void removeSession(String userId, SocketIOClient client) {
        List<SocketIOClient> userSessions = sessions.get(userId);
        if (userSessions != null) {
            userSessions.remove(client);
            if (userSessions.isEmpty()) {
                sessions.remove(userId);
            }
        }
    }

    public List<SocketIOClient> getSessions(String userId) {
        return sessions.getOrDefault(userId, List.of());
    }

    public List<SocketIOClient> getOtherSessions(String excludeUserId) {
        List<SocketIOClient> result = new CopyOnWriteArrayList<>();
        sessions.forEach((userId, clients) -> {
            if (!userId.equals(excludeUserId)) {
                result.addAll(clients);
            }
        });
        return result;
    }
}

package edu.dosw.rideci.infrastructure.websocket;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class SocketServerRunner {

    private final SocketIOServer server;

    public SocketServerRunner(SocketIOServer server) {
        this.server = server;
    }

    @PostConstruct
    public void start() {
        server.start();
        System.out.println("Socket.IO server started on port " + server.getConfiguration().getPort());
    }

    @PreDestroy
    public void stop() {
        server.stop();
        System.out.println("Socket.IO server stopped.");
    }
}

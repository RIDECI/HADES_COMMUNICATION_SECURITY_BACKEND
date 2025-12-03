package edu.dosw.rideci.infrastructure.websocket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.SocketIOClient;

import edu.dosw.rideci.application.dtos.request.SendMessageRequest;
import edu.dosw.rideci.application.dtos.response.MessageResponse;
import edu.dosw.rideci.application.service.ConversationService;
import edu.dosw.rideci.application.service.BadWordsFilter;
import edu.dosw.rideci.domain.entities.Message;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatHandler {

    private final SocketIOServer server;
    private final WebScoketIOSessionManager sessionManager;
    private final ConversationService conversationService;
    private final BadWordsFilter badWordsFilter;

    public ChatHandler(SocketIOServer server, WebScoketIOSessionManager sessionManager, ConversationService conversationService,
            BadWordsFilter badWordsFilter) {

        this.server = server;
        this.sessionManager = sessionManager;
        this.conversationService = conversationService;
        this.badWordsFilter = badWordsFilter;

        server.addConnectListener(client -> {
            String userId = client.getHandshakeData().getSingleUrlParam("userId");
            if (userId != null) {
                sessionManager.addSession(userId, client);
            }
        });

        server.addDisconnectListener(client -> {
            String userId = client.getHandshakeData().getSingleUrlParam("userId");
            if (userId != null) {
                sessionManager.removeSession(userId, client);
            }
        });

        server.addEventListener( "send-message", SendMessageRequest.class,
                new DataListener<SendMessageRequest>() {
                    @Override
                    public void onData(SocketIOClient client, SendMessageRequest data, com.corundumstudio.socketio.AckRequest ackSender) {

                        if (badWordsFilter.containsBadWords(data.getContent())) {
                            client.sendEvent(
                                    "message-error",
                                    "El mensaje contiene lenguaje no permitido"
                            );
                            return;
                        }

                        Message message = new Message(data.getConversationId(),
                                data.getReceiverId() != null
                                        ? data.getReceiverId()
                                        : "",
                                data.getSenderId(),
                                data.getContent()
                        );

                        conversationService.sendMessage(message.getConversationId(),message);

                        MessageResponse response =
                                conversationService.toMessageResponse(message);

                        if (data.getReceiverId() != null
                                && !data.getReceiverId().isEmpty()) {

                            List<SocketIOClient> receivers =
                                    sessionManager.getSessions(data.getReceiverId());

                            for (SocketIOClient c : receivers) {
                                c.sendEvent("message", response);
                            }
                        } else {
                            sessionManager.getOtherSessions(data.getSenderId())
                                    .forEach(c -> c.sendEvent("message", response));
                        }
                    }
                }
        );
    }
}

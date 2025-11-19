package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out;

import java.util.List;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.Message;

public interface MessageRepositoryPort {
    Message save(Message m);
    List<Message> findByConversationId(String id);
}

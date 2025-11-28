package edu.dosw.rideci.application.ports.out;

import java.util.List;
import edu.dosw.rideci.domain.entities.Message;

public interface MessageRepositoryPort {
    Message save(Message m);

    List<Message> findByConversationId(String id);
}

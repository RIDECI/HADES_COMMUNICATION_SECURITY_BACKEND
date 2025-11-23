package edu.dosw.rideci.infrastructure.persistance;

import java.util.List;

import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import edu.dosw.rideci.application.ports.out.MessageRepositoryPort;
import edu.dosw.rideci.domain.entities.Message;
import edu.dosw.rideci.infrastructure.repositories.MessageRepository;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryAdapter implements MessageRepositoryPort {

    private final MessageRepository messageRepository;

    @Override
    public Message save(Message m) {
        return messageRepository.save(m);
    }

    @Override
    public List<Message> findByConversationId(String id) {
        return messageRepository.findByConversationId(id);
    }
}

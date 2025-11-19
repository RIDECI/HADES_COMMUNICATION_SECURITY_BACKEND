package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.persistance;


import java.util.List;

import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out.MessageRepositoryPort;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.Message;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.repositories.MessageRepository;


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


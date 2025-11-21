package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.persistance;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out.ConversationRepositoryPort;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.Conversation;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.repositories.ConversationRepository;

@Repository
@RequiredArgsConstructor
public class ConversationRepositoryAdapter implements ConversationRepositoryPort {

    private final ConversationRepository conversationRepository;

    @Override
    public Conversation save(Conversation c) {
        return conversationRepository.save(c);
    }

    @Override
    public boolean existsById(String id) {
        return conversationRepository.existsById(id);
    }

    @Override
    public Optional<Conversation> findById(String id) {
        return conversationRepository.findById(id);
    }

}


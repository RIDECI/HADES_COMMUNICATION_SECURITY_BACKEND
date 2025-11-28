package edu.dosw.rideci.infrastructure.persistance;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import edu.dosw.rideci.application.ports.out.ConversationRepositoryPort;
import edu.dosw.rideci.domain.entities.Conversation;
import edu.dosw.rideci.infrastructure.repositories.ConversationRepository;

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

    @Override
    public Optional<Conversation> findByTripId(Long tripId) {
        return conversationRepository.findByTripId(tripId);
    }

    @Override
    public List<Conversation> findAll() {
        return conversationRepository.findAll();
    }

}

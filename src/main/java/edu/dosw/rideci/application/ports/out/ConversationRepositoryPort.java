package edu.dosw.rideci.application.ports.out;

import java.util.Optional;

import edu.dosw.rideci.domain.entities.Conversation;

public interface ConversationRepositoryPort {
    Conversation save(Conversation c);

    boolean existsById(String id);

    Optional<Conversation> findById(String id);

    Optional<Conversation> findByTripId(Long tripId);
}

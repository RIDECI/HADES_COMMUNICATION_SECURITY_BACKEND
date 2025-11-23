package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out;

import java.util.Optional;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.Conversation;

public interface ConversationRepositoryPort {
    Conversation save(Conversation c);
    boolean existsById(String id);
    Optional<Conversation> findById(String id);
    Optional<Conversation> findByTripId(Long tripId);
}

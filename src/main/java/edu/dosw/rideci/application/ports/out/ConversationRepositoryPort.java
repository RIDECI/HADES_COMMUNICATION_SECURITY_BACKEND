package edu.dosw.rideci.application.ports.out;

import java.util.List;
import java.util.Optional;

import edu.dosw.rideci.domain.entities.Conversation;

public interface ConversationRepositoryPort {
    Conversation save(Conversation c);

    boolean existsById(String id);

    Optional<Conversation> findById(String id);

    Optional<Conversation> findByTravelId(Long tripId);
    
    List<Conversation> findAll();
}

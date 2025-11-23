package edu.dosw.rideci.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import edu.dosw.rideci.domain.entities.Conversation;

public interface ConversationRepository extends MongoRepository<Conversation, String> {
    Optional<Conversation> findByTripId(Long tripId);
}

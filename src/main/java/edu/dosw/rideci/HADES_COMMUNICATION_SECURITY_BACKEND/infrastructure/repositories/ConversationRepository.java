package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.Conversation;


public interface ConversationRepository extends MongoRepository<Conversation, String> {

}

package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.Message;

/**
 * Repositorio para los mensajes.
 */
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByConversationId(String id);
}

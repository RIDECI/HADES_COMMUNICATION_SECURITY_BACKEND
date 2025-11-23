package edu.dosw.rideci.infrastructure.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.dosw.rideci.domain.entities.Message;

/**
 * Repositorio para los mensajes.
 */
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByConversationId(String id);
}

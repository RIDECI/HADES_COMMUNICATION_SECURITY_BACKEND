package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in;

import java.util.List;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.ConversationType;

/**
 * Caso de uso para crear un chat.
 */
public interface CreateConversationUseCase {
    String createChat(List<String> participants, ConversationType chatType, Long travelId);
}

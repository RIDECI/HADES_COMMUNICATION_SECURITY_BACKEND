package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.command.CreateConversationCommand;

/**
 * Caso de uso para crear un chat.
 */
public interface CreateConversationUseCase {
    String createChat(CreateConversationCommand command);

}

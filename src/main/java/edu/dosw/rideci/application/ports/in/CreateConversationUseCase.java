package edu.dosw.rideci.application.ports.in;

import edu.dosw.rideci.application.events.command.CreateConversationCommand;

/**
 * Caso de uso para crear un chat.
 */
public interface CreateConversationUseCase {
    String createChat(CreateConversationCommand command);

}

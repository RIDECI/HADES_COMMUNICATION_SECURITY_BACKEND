package edu.dosw.rideci.application.ports.in;

import edu.dosw.rideci.domain.entities.Message;

/**
 * Caso de uso para enviar un mensaje.
 */
public interface SendMessageUseCase {
    void sendMessage(String chatId, Message message);
}

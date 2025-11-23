package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.Message;

/**
 * Caso de uso para enviar un mensaje.
 */
public interface SendMessageUseCase {
    void sendMessage(String chatId, Message message);
}

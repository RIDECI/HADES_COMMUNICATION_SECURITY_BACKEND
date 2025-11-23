package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.exceptions;

/**
 * Clase de excepciones para el chat del sistema
 */
public class ConversationException extends RuntimeException {

    public ConversationException(String message) {
        super(message);
    }

    public ConversationException(String message, Throwable cause) {
        super(message, cause);
    }
}
package edu.dosw.rideci.application.exceptions;

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
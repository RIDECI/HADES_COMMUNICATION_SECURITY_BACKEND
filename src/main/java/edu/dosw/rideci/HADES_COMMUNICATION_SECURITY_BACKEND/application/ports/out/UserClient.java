package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects.UserInfo;

/**
 * Interfaz que maneja la información que llega de usuario.
 */
public interface UserClient {
    UserInfo getUserById(Long userId);
}


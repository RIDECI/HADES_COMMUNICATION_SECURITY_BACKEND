package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.adapters;

import org.springframework.stereotype.Component;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out.UserClient;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects.UserInfo;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.repositories.UserCacheRepository;

/**
 * Clase adaptador que implementa el puerto de salida UserClient.
 * Este componente proporciona acceso a la información de usuarios mediante
 * una caché local que se actualiza mediante eventos asíncronos de RabbitMQ.
 */
@Component
public class UserClientEventAdapter implements UserClient {

    private final UserCacheRepository cache;

    public UserClientEventAdapter(UserCacheRepository cache) {
        this.cache = cache;
    }

    @Override
    public UserInfo getUserById(Long id) {
        return cache.getUser(id);
    }
    
}


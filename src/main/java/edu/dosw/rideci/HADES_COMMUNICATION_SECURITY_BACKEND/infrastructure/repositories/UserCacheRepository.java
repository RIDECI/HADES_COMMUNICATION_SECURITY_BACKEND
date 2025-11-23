package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.repositories;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects.UserInfo;

/**
 * Repositorio que ayuda a guardar información recibidad del microservicio de usuarios.
 */
@Repository
public class UserCacheRepository {

    private final Map<Long, UserInfo> cache = new ConcurrentHashMap<>();

    public void saveUserInfo(UserInfo info) {
        cache.put(info.getUserId(), info);
    }

    public UserInfo getUser(Long id) {
        return cache.get(id);
    }
}

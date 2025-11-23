package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events;


import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects.UserInfo;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.config.RabbitMQConfig;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.repositories.UserCacheRepository;

/**
 * Clase componente que maneja eventos de usuarios recibidos a través de RabbitMQ.
 */
@Component
public class UserEventListener {

    private final UserCacheRepository cache;

    public UserEventListener(UserCacheRepository cache) {
        this.cache = cache;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void handleUserEvent(Map<String, Object> event) {
        System.out.println("📩 Evento recibido: " + event);

        Map<String, Object> data = (Map<String, Object>) event.get("data");

        Long userId = Long.parseLong(data.get("userId").toString());

        UserInfo user = new UserInfo(
                userId,
                data.get("name").toString(),
                data.get("email").toString()
        );

        cache.saveUserInfo(user);
    }


}

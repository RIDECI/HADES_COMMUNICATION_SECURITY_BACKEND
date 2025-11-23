package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out.EventPublisher;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitEventPublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(Object event, String exchange, String routingKey) {
        rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                event
        );
    }
}



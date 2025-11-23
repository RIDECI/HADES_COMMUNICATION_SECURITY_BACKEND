package edu.dosw.rideci.application.events;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static edu.dosw.rideci.infrastructure.config.RabbitMQConfig.*;

@Service
public class ConversationMessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public ConversationMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(MessageSentEvent event) {

        rabbitTemplate.convertAndSend(
                CHAT_EXCHANGE,
                CHAT_ROUTING_KEY,
                event);

        System.out.println("📨 Mensaje enviado al exchange de chat");
    }
}

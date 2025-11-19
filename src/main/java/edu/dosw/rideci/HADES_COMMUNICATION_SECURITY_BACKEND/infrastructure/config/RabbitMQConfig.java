package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de RabbitMQ para el módulo de Comunicación y Seguridad. Define los componentes de mensajería necesarios 
 * para la comunicación asíncrona con otros microservicios del sistema HADES, específicamente para eventos de usuarios.
 */
@Configuration
public class RabbitMQConfig {

    public static final String USER_EXCHANGE = "user.exchange";
    public static final String USER_QUEUE = "user.events.queue";
    public static final String USER_ROUTING_KEY = "user.events";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(USER_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(USER_QUEUE, true);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(USER_ROUTING_KEY);
    }
}

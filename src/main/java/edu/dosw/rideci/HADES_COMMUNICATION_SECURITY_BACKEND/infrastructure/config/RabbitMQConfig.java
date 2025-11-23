package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String CHAT_EXCHANGE = "rideci.chat.exchange";
    public static final String CHAT_ROUTING_KEY = "chat.message";
    public static final String CHAT_QUEUE = "rideci.chat.queue";

    @Bean
    public TopicExchange chatExchange() {
        return new TopicExchange(CHAT_EXCHANGE, true, false);
    }

    @Bean
    public Queue chatQueue() {
        return new Queue(CHAT_QUEUE, true);
    }

    @Bean
    public Binding chatBinding() {
        return BindingBuilder
                .bind(chatQueue())
                .to(chatExchange())
                .with(CHAT_ROUTING_KEY);
    }

    public static final String TRIP_EXCHANGE = "travel.exchange";

    public static final String TRIP_CREATED_ROUTING_KEY = "travel.created";
    public static final String TRIP_CREATED_QUEUE = "rideci.trip.created.queue";

    public static final String TRIP_FINISHED_ROUTING_KEY = "travel.completed";
    public static final String TRIP_FINISHED_QUEUE = "rideci.trip.finished.queue";

    @Bean
    public TopicExchange tripExchange() {
        return new TopicExchange(TRIP_EXCHANGE, true, false);
    }

    @Bean
    public Queue tripCreatedQueue() {
        return new Queue(TRIP_CREATED_QUEUE, true);
    }

    @Bean
    public Queue tripFinishedQueue() {
        return new Queue(TRIP_FINISHED_QUEUE, true);
    }

    @Bean
    public Binding tripCreatedBinding() {
        return BindingBuilder
                .bind(tripCreatedQueue())
                .to(tripExchange())
                .with(TRIP_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding tripFinishedBinding() {
        return BindingBuilder
                .bind(tripFinishedQueue())
                .to(tripExchange())
                .with(TRIP_FINISHED_ROUTING_KEY);
    }


    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }

}

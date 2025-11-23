package edu.dosw.rideci.application.ports.out;

public interface EventPublisher {
    void publish(Object event, String exchange, String routingKey);
}

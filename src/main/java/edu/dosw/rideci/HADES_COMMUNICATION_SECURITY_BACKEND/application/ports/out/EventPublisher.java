package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out;

public interface EventPublisher { 
    void publish(Object event, String exchange, String routingKey); 
}



package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.service.TripEventService;

@Component
public class TravelEventListener {

    private final TripEventService tripEventService;

    public TravelEventListener(TripEventService tripEventService) {
        this.tripEventService = tripEventService;
    }

    @RabbitListener(queues = "rideci.trip.created.queue")
    public void handleTripCreated(TravelCreatedEvent event) {
        tripEventService.processTripCreated(event);
    }

}

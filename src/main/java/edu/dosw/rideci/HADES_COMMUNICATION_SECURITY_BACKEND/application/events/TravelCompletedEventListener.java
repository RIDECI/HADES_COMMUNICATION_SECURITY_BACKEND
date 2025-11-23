package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.service.TripEventService;

@Component
public class TravelCompletedEventListener {

    private final TripEventService tripEventService;

    public TravelCompletedEventListener(TripEventService tripEventService) {
        this.tripEventService = tripEventService;
    }

    @RabbitListener(queues = "rideci.trip.finished.queue")
    public void handleTripFinished(TravelCompletedEvent event) {
        tripEventService.processTripFinished(event);
    }
}

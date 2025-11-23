package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.service.TripEventService;

@Component
public class TripFinishEventListener {

    private final TripEventService tripEventService;

    public TripFinishEventListener(TripEventService tripEventService) {
        this.tripEventService = tripEventService;
    }

    @RabbitListener(queues = "rideci.trip.finished.queue")
    public void handleTripFinished(TripFinishEvent event) {
        tripEventService.processTripFinished(event);
    }
}

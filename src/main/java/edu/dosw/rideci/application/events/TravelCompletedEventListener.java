package edu.dosw.rideci.application.events;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import edu.dosw.rideci.application.service.TripEventService;

@Component
public class TravelCompletedEventListener {

    private final TripEventService tripEventService;

    public TravelCompletedEventListener(TripEventService tripEventService) {
        this.tripEventService = tripEventService;
    }

    @RabbitListener(queues = "security.travel.completed.queue")
    public void handleTripFinished(TravelCompletedEvent event) {
        tripEventService.processTripFinished(event);
    }
}

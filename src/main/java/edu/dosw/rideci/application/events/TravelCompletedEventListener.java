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
        try {
            System.out.println("✅ Evento TravelCompleted recibido en HADES");
            System.out.println("📦 Contenido del evento: " + event);
            tripEventService.processTripFinished(event);
            System.out.println("✅ Evento completado procesado exitosamente");
        } catch (Exception e) {
            System.err.println("❌ Error procesando TravelCompletedEvent: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}

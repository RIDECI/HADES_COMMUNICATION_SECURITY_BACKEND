package edu.dosw.rideci.application.events;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import edu.dosw.rideci.application.service.TripEventService;

@Component
public class TravelEventListener {

    private final TripEventService tripEventService;

    public TravelEventListener(TripEventService tripEventService) {
        this.tripEventService = tripEventService;
    }

    @RabbitListener(queues = "security.travel.created.queue")
    public void handleTripCreated(TravelCreatedEvent event) {
        try {
            System.out.println("✅ Evento TravelCreated recibido en HADES");
            System.out.println("📦 Contenido del evento: " + event);
            tripEventService.processTripCreated(event);
            System.out.println("✅ Evento procesado exitosamente");
        } catch (Exception e) {
            System.err.println("❌ Error procesando TravelCreatedEvent: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}

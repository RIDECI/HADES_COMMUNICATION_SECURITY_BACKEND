package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.service;

import org.springframework.stereotype.Service;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.TripCreatedEvent;

@Service
public class TripEventService {

    public void processTripCreated(TripCreatedEvent event) {

        System.out.println("🔧 Procesando TripCreatedEvent...");
        System.out.println("🆔 Trip ID: " + event.getTravelId());
        System.out.println("🚗 Driver: " + event.getDriverId());

    }
}

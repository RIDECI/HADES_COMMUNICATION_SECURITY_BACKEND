package edu.dosw.rideci.application.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import edu.dosw.rideci.application.events.TravelCreatedEvent;
import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.application.events.command.CreateConversationCommand;
import edu.dosw.rideci.application.ports.in.CreateConversationUseCase;
import edu.dosw.rideci.application.ports.in.UpdateConversationStatusUseCase;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripEventService {

    private final CreateConversationUseCase createConversationUseCase;
    private final UpdateConversationStatusUseCase updateConversationStatusUseCase;

    public void processTripCreated(TravelCreatedEvent event) {

        System.out.println("🔧 Procesando TripCreatedEvent...");
        System.out.println("🆔 Trip ID: " + event.getTravelId());
        System.out.println("🚗 Driver: " + event.getDriverId());
        System.out.println("👤 Organizer: " + event.getOrganizerId());
        System.out.println("📦 TravelType: " + event.getTravelType());

        var participantIds = new ArrayList<Long>();

        switch (event.getTravelType()) {

            case TRIP:
                if (event.getDriverId() != null) {
                    participantIds.add(event.getDriverId());
                }
                if (event.getPassengersId() != null) {
                    participantIds.addAll(event.getPassengersId());
                }
                break;

            case GROUP:
                if (event.getOrganizerId() != null) {
                    participantIds.add(event.getOrganizerId());
                }
                if (event.getPassengersId() != null) {
                    participantIds.addAll(event.getPassengersId());
                }
                break;
        }

        CreateConversationCommand command = CreateConversationCommand.builder()
                .tripId(event.getTravelId())
                .participants(participantIds)
                .organizerId(event.getOrganizerId())
                .chatType(event.getTravelType())
                .travelStatus(event.getState())
                .build();

        createConversationUseCase.createChat(command);
    }

    public void processTripFinished(TravelCompletedEvent event) {

        System.out.println("🔧 Procesando TripFinishEvent...");
        System.out.println("🆔 Trip ID: " + event.getTravelId());
        System.out.println("📌 Nuevo estado: " + event.getState());

        updateConversationStatusUseCase.updateStatus(
                event.getTravelId(),
                event.getState());
    }
}

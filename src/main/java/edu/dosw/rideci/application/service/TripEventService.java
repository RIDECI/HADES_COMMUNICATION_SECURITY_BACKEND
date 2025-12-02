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

        var participantIds = event.getPassengersId() != null
                ? new ArrayList<>(event.getPassengersId())
                : new ArrayList<Long>();

        if (event.getDriverId() != null) {
            participantIds.add(event.getDriverId());
        }


        CreateConversationCommand command = CreateConversationCommand.builder()
            .travelId(event.getTravelId())
            .organizerId(event.getOrganizerId())
            .driverId(event.getDriverId())
            .participants(participantIds)
            .chatType(event.getTravelType())
            .travelStatus(event.getStatus()) 
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
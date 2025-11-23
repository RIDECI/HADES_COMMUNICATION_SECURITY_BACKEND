package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.service;

import org.springframework.stereotype.Service;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.TripCreatedEvent;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.TripFinishEvent;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.command.CreateConversationCommand;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in.CreateConversationUseCase;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in.UpdateConversationStatusUseCase;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripEventService {

    private final CreateConversationUseCase createConversationUseCase;
    private final UpdateConversationStatusUseCase updateConversationStatusUseCase;

    public void processTripCreated(TripCreatedEvent event) {

        System.out.println("🔧 Procesando TripCreatedEvent...");
        System.out.println("🆔 Trip ID: " + event.getTravelId());
        System.out.println("🚗 Driver: " + event.getDriverId());

 
        var participantIds = event.getPassangerId()
                .stream()
                .map(p -> p.getUserId())
                .toList();

  
        CreateConversationCommand command = CreateConversationCommand.builder()
                .participants(participantIds)
                .chatType(event.getConversationType())
                .tripId(event.getTravelId())
                .travelStatus(event.getStatus())     
                .build();

        createConversationUseCase.createChat(command);
    }

    public void processTripFinished(TripFinishEvent event) {

        System.out.println("🔧 Procesando TripFinishEvent...");
        System.out.println("🆔 Trip ID: " + event.getId());
        System.out.println("📌 Nuevo estado: " + event.getTravelStatus());

        updateConversationStatusUseCase.updateStatus(
                event.getId(),
                event.getTravelStatus()
        );
    }
}

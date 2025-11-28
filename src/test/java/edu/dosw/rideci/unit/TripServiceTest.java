package edu.dosw.rideci.unit;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.application.events.TravelCreatedEvent;
import edu.dosw.rideci.application.events.command.CreateConversationCommand;
import edu.dosw.rideci.application.ports.in.CreateConversationUseCase;
import edu.dosw.rideci.application.ports.in.UpdateConversationStatusUseCase;
import edu.dosw.rideci.application.service.TripEventService;
import edu.dosw.rideci.domain.enums.Status;
import edu.dosw.rideci.domain.enums.TravelType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

class TripEventServiceTest {

    @Mock
    private CreateConversationUseCase createConversationUseCase;

    @Mock
    private UpdateConversationStatusUseCase updateConversationStatusUseCase;

    @InjectMocks
    private TripEventService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processTripCreated_ok() {

        TravelCreatedEvent event = TravelCreatedEvent.builder()
                .travelId(50L)
                .driverId(7L)
                .status(Status.ACTIVE)
                .origin(null)       
                .destiny(null)
                .passengersId(List.of(1L, 2L))
                .travelType(TravelType.TRIP)
                .departureDateAndTime(LocalDateTime.now())
                .build();

        when(createConversationUseCase.createChat(any(CreateConversationCommand.class)))
                .thenReturn("conversation-123");

        service.processTripCreated(event);

        verify(createConversationUseCase, times(1))
                .createChat(any(CreateConversationCommand.class));
    }


    @Test
    void processTripFinished_ok() {

        TravelCompletedEvent event = TravelCompletedEvent.builder()
                .travelId(50L)
                .driverId(7L)
                .passengerList(List.of(1L, 2L))
                .state(Status.COMPLETED)
                .build();

        doNothing().when(updateConversationStatusUseCase)
                .updateStatus(event.getTravelId(), event.getState());

        service.processTripFinished(event);

        verify(updateConversationStatusUseCase, times(1))
                .updateStatus(50L, Status.COMPLETED);
    }

}

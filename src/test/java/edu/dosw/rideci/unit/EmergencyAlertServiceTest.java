package edu.dosw.rideci.unit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import edu.dosw.rideci.application.dtos.response.EmergencyAlertResponse;
import edu.dosw.rideci.application.events.EmergencyAlertEvent;
import edu.dosw.rideci.application.dtos.request.EmergencyAlertRequest;
import edu.dosw.rideci.application.mappers.EmergencyAlertMapper;
import edu.dosw.rideci.application.ports.out.EmergencyAlertRepositoryPort;
import edu.dosw.rideci.application.ports.out.EventPublisher;
import edu.dosw.rideci.application.service.EmergencyAlertService;
import edu.dosw.rideci.domain.entities.EmergencyAlert;
import edu.dosw.rideci.domain.valueobjects.Location;
import edu.dosw.rideci.infrastructure.config.RabbitMQConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class EmergencyAlertServiceTest {

    @Mock
    private EmergencyAlertRepositoryPort repo;

    @Mock
    private EmergencyAlertMapper mapper;

    @Mock
    private EventPublisher publisher;

    @InjectMocks
    private EmergencyAlertService service;

    private EmergencyAlert entity;
    private EmergencyAlertResponse dtoResponse;
    private Location location;

    @BeforeEach
    void setUp() {

        location = Location.builder()
                .longitude(4.567)
                .latitude(6.789)
                .direction("North-East")
                .build();

        entity = new EmergencyAlert();
        entity.setId("alert123");
        entity.setUserId(10L);
        entity.setTripId(50L);
        entity.setCurrentLocation(location);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setAdditionalInfo("Emergency button activated");

        dtoResponse = new EmergencyAlertResponse();
        dtoResponse.setId("alert123");
        dtoResponse.setUserId(10L);
        dtoResponse.setTripId(50L);
        dtoResponse.setCurrentLocation(location);
        dtoResponse.setCreatedAt(LocalDateTime.now());
        dtoResponse.setAdditionalInfo("Emergency button activated");
    }


    @Test
    void testSouldSaveAndPublishEvent() {

        Long userId = 10L;
        Long tripId = 50L;

        when(mapper.toEntity(any(EmergencyAlertRequest.class))).thenReturn(entity);

        service.activate(userId, tripId, location);

        verify(mapper, times(1)).toEntity(any(EmergencyAlertRequest.class));
        verify(repo, times(1)).save(entity);

        verify(publisher, times(1)).publish(
                any(EmergencyAlertEvent.class),
                eq(RabbitMQConfig.EMERGENCY_EXCHANGE),
                eq(RabbitMQConfig.EMERGENCY_CREATED_ROUTING_KEY)
        );
    }

    @Test
    void testShouldGetAlert() {
        when(repo.findById("alert123")).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dtoResponse);

        EmergencyAlertResponse res = service.getAlert("alert123");

        assertNotNull(res);
        assertEquals("alert123", res.getId());
        assertEquals(10L, res.getUserId());
        assertEquals(4.567, res.getCurrentLocation().getLongitude());
        assertEquals("North-East", res.getCurrentLocation().getDirection());
    }

    @Test
    void testShouldNotGetAlert() {
        when(repo.findById("missing")).thenReturn(Optional.empty());

        EmergencyAlertResponse res = service.getAlert("missing");

        assertNull(res);
    }

    @Test
    void testShouldGetAllAlerts() {
        when(repo.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dtoResponse);

        List<EmergencyAlertResponse> result = service.getAllAlerts();

        assertEquals(1, result.size());
        assertEquals("alert123", result.get(0).getId());
    }

    @Test
    void testShouldNotGetAllAlerts() {
        when(repo.findAll()).thenReturn(List.of());

        List<EmergencyAlertResponse> result = service.getAllAlerts();

        assertEquals(0, result.size());
    }
}

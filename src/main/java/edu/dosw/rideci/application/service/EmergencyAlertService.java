package edu.dosw.rideci.application.service;


import edu.dosw.rideci.application.dtos.request.EmergencyAlertRequest;
import edu.dosw.rideci.application.dtos.response.EmergencyAlertResponse;
import edu.dosw.rideci.application.events.EmergencyAlertEvent;
import edu.dosw.rideci.application.mappers.EmergencyAlertMapper;
import edu.dosw.rideci.application.ports.in.ActivateEmergencyButtonUseCase;
import edu.dosw.rideci.application.ports.out.EmergencyAlertRepositoryPort;
import edu.dosw.rideci.application.ports.out.EventPublisher;
import edu.dosw.rideci.domain.entities.EmergencyAlert;
import edu.dosw.rideci.domain.valueobjects.Location;
import edu.dosw.rideci.infrastructure.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EmergencyAlertService implements ActivateEmergencyButtonUseCase {

    private final EmergencyAlertRepositoryPort emergencyAlertRepository;
    private final EmergencyAlertMapper mapper;
    private final EventPublisher eventPublisher;


    @Override
    public void activate(Long userId, Long tripId, Location currentLocation) {

        EmergencyAlertRequest dto = new EmergencyAlertRequest();
        dto.setUserId(userId);
        dto.setTravelId(tripId);
        dto.setCurrentLocation(currentLocation);

        EmergencyAlert alert = mapper.toEntity(dto);
        alert.setCreatedAt(LocalDateTime.now());
        alert.setAdditionalInfo("Emergency button activated");


        emergencyAlertRepository.save(alert);


        EmergencyAlertEvent event = EmergencyAlertEvent.builder()
                .id(alert.getId())
                .travelId(alert.getTravelId())
                .userId(alert.getUserId())
                .type("EMERGENCY")
                .createdAt(alert.getCreatedAt())
                .build();


        eventPublisher.publish(
                event,
                RabbitMQConfig.EMERGENCY_EXCHANGE,
                RabbitMQConfig.EMERGENCY_CREATED_ROUTING_KEY
        );
    }

    public EmergencyAlertResponse getAlert(String id) {
        return emergencyAlertRepository.findById(id)
                .map(mapper::toDTO)
                .orElse(null);
    }
    public List<EmergencyAlertResponse> getAllAlerts() {
        return emergencyAlertRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

}
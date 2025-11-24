package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.service;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.request.EmergencyAlertRequest;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response.EmergencyAlertResponse;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.mappers.EmergencyAlertMapper;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in.ActivateEmergencyButtonUseCase;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out.EmergencyAlertRepositoryPort;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.EmergencyAlert;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects.LocationVO;
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



    @Override
    public void activate(Long userId, Long tripId, LocationVO currentLocation) {

        EmergencyAlertRequest dto = new EmergencyAlertRequest();
        dto.setUserId(userId);
        dto.setTripId(tripId);
        dto.setCurrentLocation(currentLocation);

        EmergencyAlert alert = mapper.toEntity(dto);
        alert.setCreatedAt(LocalDateTime.now());
        alert.setAdditionalInfo("Emergency button activated");

        emergencyAlertRepository.save(alert);
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

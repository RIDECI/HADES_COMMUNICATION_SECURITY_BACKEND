package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects.LocationVO;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EmergencyAlertResponse {
    private String id;
    private Long userId;
    private Long tripId;
    private LocationVO currentLocation;
    private LocalDateTime createdAt;
    private String additionalInfo;
}

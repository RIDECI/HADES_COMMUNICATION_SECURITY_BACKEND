package edu.dosw.rideci.application.dtos.response;


import edu.dosw.rideci.domain.valueobjects.Location;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EmergencyAlertResponse {
    private String id;
    private Long userId;
    private Long tripId;
    private Location currentLocation;
    private LocalDateTime createdAt;
    private String additionalInfo;
}
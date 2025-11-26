package edu.dosw.rideci.application.dtos.request;

import edu.dosw.rideci.domain.valueobjects.Location;
import lombok.Data;

@Data
public class EmergencyAlertRequest {

    private Long userId;
    private Long tripId;
    private Location currentLocation;
}
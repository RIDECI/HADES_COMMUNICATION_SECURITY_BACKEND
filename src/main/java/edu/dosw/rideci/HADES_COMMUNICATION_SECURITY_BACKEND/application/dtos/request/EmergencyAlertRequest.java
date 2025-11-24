package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.request;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects.LocationVO;
import lombok.Data;

@Data
public class EmergencyAlertRequest {

        private Long userId;
        private Long tripId;
        private LocationVO currentLocation;
}


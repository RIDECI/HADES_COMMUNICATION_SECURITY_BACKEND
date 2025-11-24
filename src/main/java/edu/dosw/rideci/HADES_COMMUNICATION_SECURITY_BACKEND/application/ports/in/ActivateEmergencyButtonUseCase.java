package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects.LocationVO;

public interface ActivateEmergencyButtonUseCase {
    void activate(Long userId, Long tripId, LocationVO currentLocation);
}

package edu.dosw.rideci.application.ports.in;


import edu.dosw.rideci.domain.valueobjects.Location;

public interface ActivateEmergencyButtonUseCase {
    void activate(Long userId, Long tripId, Location currentLocation);
}
package edu.dosw.rideci.application.ports.in;

import edu.dosw.rideci.domain.valueobjects.Location;

public interface DeviationDetectedUseCase {
    void deviationDetected(Long userId, Location location, Long tripId, Long targetId);
}

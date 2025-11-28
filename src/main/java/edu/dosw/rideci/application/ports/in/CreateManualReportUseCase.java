package edu.dosw.rideci.application.ports.in;

import edu.dosw.rideci.domain.valueobjects.Location;

public interface CreateManualReportUseCase {
    void createManual(Long userId, Long targetId, Location location, String description, Long tripId);
}

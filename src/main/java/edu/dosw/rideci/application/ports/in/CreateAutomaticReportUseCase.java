package edu.dosw.rideci.application.ports.in;

import edu.dosw.rideci.domain.valueobjects.Location;

public interface CreateAutomaticReportUseCase {
    void createAutomatic(Long userId, Location location,Long tripId,Long targetId);
}

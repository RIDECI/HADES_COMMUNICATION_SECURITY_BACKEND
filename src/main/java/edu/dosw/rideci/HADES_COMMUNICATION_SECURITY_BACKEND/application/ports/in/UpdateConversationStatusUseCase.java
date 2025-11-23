package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.TravelStatus;

public interface UpdateConversationStatusUseCase {
    void updateStatus(Long tripId, TravelStatus status);
}

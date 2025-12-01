package edu.dosw.rideci.application.ports.in;

import edu.dosw.rideci.domain.enums.Status;

public interface UpdateConversationStatusUseCase {
    void updateStatus(String tripId, Status status);
}

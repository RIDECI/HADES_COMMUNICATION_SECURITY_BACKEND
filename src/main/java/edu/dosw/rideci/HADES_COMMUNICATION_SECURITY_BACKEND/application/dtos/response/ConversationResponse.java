package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response;

import java.util.List;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.TravelType;
import lombok.Data;

@Data
public class ConversationResponse {
    private String id;
    private Long tripId;
    private TravelType type;
    private boolean active;
    private List<Long> participants;
}


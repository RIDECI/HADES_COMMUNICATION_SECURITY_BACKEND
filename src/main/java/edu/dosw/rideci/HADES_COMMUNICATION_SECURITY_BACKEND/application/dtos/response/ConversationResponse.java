package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response;

import java.util.List;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.Participant;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.ConversationType;
import lombok.Data;

@Data
public class ConversationResponse {
    private String id;
    private Long tripId;
    private ConversationType type;
    private boolean active;
    private List<Participant> participants;
}


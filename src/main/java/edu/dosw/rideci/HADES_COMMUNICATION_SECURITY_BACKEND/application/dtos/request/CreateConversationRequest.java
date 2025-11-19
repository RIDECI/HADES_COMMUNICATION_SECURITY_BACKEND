package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.request;

import java.util.List;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.ConversationType;

import lombok.Data;

@Data
public class CreateConversationRequest {
    private Long tripId;                         
    private ConversationType type;               
    private List<ParticipantRequest> participants;
}

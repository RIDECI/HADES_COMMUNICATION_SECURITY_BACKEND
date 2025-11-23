package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.request;

import java.util.List;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.TravelType;

import lombok.Data;

@Data
public class CreateConversationRequest {
    private Long tripId;
    private TravelType type;
    private List<Long> participants;  
}

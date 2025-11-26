package edu.dosw.rideci.application.dtos.request;

import java.util.List;

import edu.dosw.rideci.domain.enums.TravelType;

import lombok.Data;

@Data
public class CreateConversationRequest {
    private Long tripId;
    private TravelType type;
    private List<Long> participants;
}

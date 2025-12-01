package edu.dosw.rideci.application.dtos.response;

import java.util.List;

import edu.dosw.rideci.domain.enums.TravelType;
import lombok.Data;

@Data
public class ConversationResponse {
    private String id;
    private String travelId;
    private Long organizerId;
    private Long driverId;
    private TravelType type;
    private boolean active;
    private List<Long> participants;
}

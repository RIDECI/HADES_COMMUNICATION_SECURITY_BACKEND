package edu.dosw.rideci.application.dtos.response;

import java.util.List;

import edu.dosw.rideci.domain.enums.TravelType;
import lombok.Data;

@Data
public class ConversationResponse {
    private String id;
    private Long travelId;
    private Long organizerId;
    private Long driveId;
    private TravelType type;
    private boolean active;
    private List<Long> participants;
}

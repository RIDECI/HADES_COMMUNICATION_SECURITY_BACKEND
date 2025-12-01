package edu.dosw.rideci.application.events.command;

import java.util.List;

import edu.dosw.rideci.domain.enums.TravelType;
import edu.dosw.rideci.domain.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateConversationCommand {

    private List<Long> participants;

    private TravelType chatType;

    private Status travelStatus;

    private String travelId;

    private Long organizerId;
    
    private Long driverId;

    
}
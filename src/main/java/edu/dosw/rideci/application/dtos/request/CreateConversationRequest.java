package edu.dosw.rideci.application.dtos.request;

import java.util.List;

import edu.dosw.rideci.domain.enums.Status;
import edu.dosw.rideci.domain.enums.TravelType;

import lombok.Data;

@Data
public class CreateConversationRequest {

    private Long travelId;
    private TravelType type;     
    
    private Long organizerId;    
    private Long driverId;       
    
    private List<Long> participants;
    private Status travelStatus;
}


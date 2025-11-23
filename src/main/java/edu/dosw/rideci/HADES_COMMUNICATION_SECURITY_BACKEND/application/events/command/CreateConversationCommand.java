package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.command;

import java.util.List;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.TravelType;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateConversationCommand {

    private List<Long> participants;

    private TravelType chatType;

    private Status travelStatus;

    private Long tripId;
}

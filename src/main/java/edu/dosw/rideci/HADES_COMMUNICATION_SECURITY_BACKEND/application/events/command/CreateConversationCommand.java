package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.command;

import java.util.List;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.ConversationType;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.TravelStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateConversationCommand {

    private List<String> participants;

    private ConversationType chatType;

    private TravelStatus travelStatus;

    private Long tripId;
}

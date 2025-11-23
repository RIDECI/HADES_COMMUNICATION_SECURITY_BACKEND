package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events;

import java.time.LocalDateTime;
import java.util.List;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.ConversationType;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.TravelStatus;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.models.Participant;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects.LocationVO;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripCreatedEvent {

    private Long travelId;
    private Long driverId;
    private TravelStatus status;
    private LocationVO origin;
    private LocationVO destiny;
    private List<Participant> passangerId;
    private LocalDateTime departureDateAndTime;
    private ConversationType conversationType;
}

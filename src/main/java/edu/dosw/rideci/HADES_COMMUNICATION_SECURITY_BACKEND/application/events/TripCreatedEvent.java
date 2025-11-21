package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events;

import java.time.LocalDateTime;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects.LocationVO;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripCreatedEvent {

    private Long travelId;
    private Long driverId;
    private LocationVO origin;
    private LocationVO destiny;
    private LocalDateTime departureDateAndTime;
}

package edu.dosw.rideci.application.events;

import java.time.LocalDateTime;
import java.util.List;

import edu.dosw.rideci.domain.enums.TravelType;
import edu.dosw.rideci.domain.enums.Status;
import edu.dosw.rideci.domain.valueobjects.Location;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelCreatedEvent {

    private Long travelId;
    private Long driverId;
    private Status state;
    private Location origin;
    private Location destiny;
    private List<Long> passengersId;
    private TravelType travelType;
    private LocalDateTime departureDateAndTime;
}

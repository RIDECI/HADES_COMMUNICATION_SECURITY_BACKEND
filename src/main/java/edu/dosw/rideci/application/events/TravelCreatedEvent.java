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
    private Long organizerId;
    private Long driverId;
    private int availableSlots;
    private Status state;
    private TravelType travelType;
    private double estimatedCost;
    private LocalDateTime departureDateAndTime;
    private List<Long> passengersId;
    private String conditions;
    private Location origin;
    private Location destiny;
}

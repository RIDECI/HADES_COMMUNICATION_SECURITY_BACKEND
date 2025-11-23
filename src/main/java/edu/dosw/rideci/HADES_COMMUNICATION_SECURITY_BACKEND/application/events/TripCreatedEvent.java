package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events;

import java.time.LocalDateTime;
import java.util.List;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.TravelType;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.Status;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects.Location;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripCreatedEvent {

    private Long travelId;
    private Long driverId;
    private Status state;                
    private Location origin;             
    private Location destiny;           
    private List<Long> passengersId;    
    private TravelType travelType;      
    private LocalDateTime departureDateAndTime;
}

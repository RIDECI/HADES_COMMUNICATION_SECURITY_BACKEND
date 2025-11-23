package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.TravelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripFinishEvent {
    private Long id;
    private TravelStatus travelStatus;
}

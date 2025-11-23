package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelCompletedEvent {
    private Long travelId;
    private Status state;
}

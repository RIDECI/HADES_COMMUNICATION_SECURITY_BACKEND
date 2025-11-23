package edu.dosw.rideci.application.events;

import edu.dosw.rideci.domain.enums.Status;
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

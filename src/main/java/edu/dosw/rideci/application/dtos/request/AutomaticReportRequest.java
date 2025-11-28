package edu.dosw.rideci.application.dtos.request;

import edu.dosw.rideci.domain.valueobjects.Location;
import lombok.Data;

@Data

public class AutomaticReportRequest {
    private Long userId;
    private Long tripId;
    private Location location;
    private Long targetId;

    private Long travelId;
    private Location currentLocation;
}

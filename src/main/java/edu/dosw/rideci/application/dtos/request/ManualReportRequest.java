package edu.dosw.rideci.application.dtos.request;

import edu.dosw.rideci.domain.valueobjects.Location;
import lombok.Data;

@Data
public class ManualReportRequest {
    private Long userId;
    private Long tripId;
    private Long targetId;
    private Location location;
    private String description;

}
package edu.dosw.rideci.application.dtos.request;


import edu.dosw.rideci.domain.enums.ReportType;
import edu.dosw.rideci.domain.valueobjects.Location;
import lombok.Data;

@Data
public class ReportRequest {

    private ReportType type;
    private Long userId;
    private Long targetId;
    private Long tripId;
    private Location location;
    private String description;
}


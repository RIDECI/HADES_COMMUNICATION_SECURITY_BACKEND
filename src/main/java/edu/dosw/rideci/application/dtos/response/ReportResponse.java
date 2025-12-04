package edu.dosw.rideci.application.dtos.response;


import edu.dosw.rideci.domain.enums.ReportStatus;
import edu.dosw.rideci.domain.enums.ReportType;
import edu.dosw.rideci.domain.valueobjects.Location;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportResponse {

    private String id;
    private ReportType type;
    private Long userId;
    private Long targetId;

    private Long tripId;

    private Location location;
    private String description;
    private LocalDateTime createdAt;
    private ReportStatus status;
    private String evidence;

}


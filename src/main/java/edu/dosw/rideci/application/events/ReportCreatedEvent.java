package edu.dosw.rideci.application.events;

import edu.dosw.rideci.domain.enums.ReportStatus;
import edu.dosw.rideci.domain.enums.ReportType;
import edu.dosw.rideci.domain.valueobjects.Location;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReportCreatedEvent {
    private String reportId;
    private Long userId;
    private Long targetId;
    private Long tripId;
    private ReportType type;
    private Location location;
    private String description;
    private LocalDateTime createdAt;
    private ReportStatus status;
    private String evidence;
}

package edu.dosw.rideci.domain.entities;

import edu.dosw.rideci.domain.enums.ManualReason;
import edu.dosw.rideci.domain.enums.ReportStatus;
import edu.dosw.rideci.domain.enums.ReportType;
import edu.dosw.rideci.domain.valueobjects.Location;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reports")
public class Report {

    @Id
    private String id;
    private Long tripId;

    private ReportType type;

    private Long userId;
    private Long targetId;

    private Location location;

    private String description;
    private ReportStatus status;
    private LocalDateTime createdAt;
    private ManualReason manualReason;  // null para AUTOMATIC y EMERGENCY
    private String evidence;
}

package edu.dosw.rideci.domain.entities;

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

    private LocalDateTime createdAt;
}

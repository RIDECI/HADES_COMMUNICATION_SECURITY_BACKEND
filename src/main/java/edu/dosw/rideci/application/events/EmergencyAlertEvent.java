package edu.dosw.rideci.application.events;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EmergencyAlertEvent {
    private String id;

    private Long travelId;

    private Long userId;

    private String type;

    private LocalDateTime createdAt;
}
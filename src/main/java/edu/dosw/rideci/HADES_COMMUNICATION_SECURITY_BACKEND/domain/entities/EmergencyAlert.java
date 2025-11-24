package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects.LocationVO;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una alerta de emergencia activada durante un viaje.
 */
@Data
@NoArgsConstructor
@Document(collection = "emergency_alerts")
public class EmergencyAlert {
    @Id
    private String id;
    @Indexed
    private Long tripId;
    private String conversationId;
    @Indexed
    private String type;
    private Long userId;
    private LocationVO currentLocation;
    private String additionalInfo;
    @Indexed
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;
    private String resolvedBy;
    private String resolutionNotes;
    private List<String> actionsTaken = new ArrayList<>();


}
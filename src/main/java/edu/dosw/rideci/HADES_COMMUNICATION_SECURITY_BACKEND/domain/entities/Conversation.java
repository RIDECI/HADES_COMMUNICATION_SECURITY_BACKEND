package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities;

import java.util.Date;
import java.util.List;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.TravelType;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.Status;

import org.springframework.data.annotation.Id;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "conversations")
@Data
public class Conversation {
    @Id
    private String id;
    private Long tripId; 
    private TravelType type;
    private Status travelStatus;
    private boolean active = false;
    private List<Long> participants;
    private Date createdAt = new Date();
    private Date updatedAt = new Date();
}

package edu.dosw.rideci.domain.entities;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import edu.dosw.rideci.domain.enums.TravelType;
import edu.dosw.rideci.domain.enums.Status;

import org.springframework.data.annotation.Id;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "conversations")
@Data
public class Conversation {
    @Id
    private String id = UUID.randomUUID().toString();
    private String travelId;
    private Long organizerId;
    private Long driverId;
    private TravelType type;
    private Status travelStatus;
    private boolean active = false;
    private List<Long> participants;
    private Date createdAt = new Date();
    private Date updatedAt = new Date();
}

package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.ConversationType;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.TravelStatus;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.models.Participant;

import org.springframework.data.annotation.Id;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "conversations")
@Data
public class Conversation {
    @Id
    private String id;
    private Long tripId; 
    private ConversationType type;
    private TravelStatus travelStatus;
    private boolean active = false;
    private List<Participant> participants = new ArrayList<>();
    private Date createdAt = new Date();
    private Date updatedAt = new Date();
}

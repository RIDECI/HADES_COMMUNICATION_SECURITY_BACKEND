package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response;

import lombok.Data;

@Data
public class ParticipantResponse {
    private String userId;
    private String name;
    private String avatarUrl;
    private boolean isDriver;
    private long unreadCount;
}


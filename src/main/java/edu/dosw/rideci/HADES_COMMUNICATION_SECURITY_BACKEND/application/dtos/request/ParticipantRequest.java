package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.request;

import lombok.Data;

@Data
public class ParticipantRequest {
    private String userId;
    private String name;
    private String avatarUrl;
    private boolean isDriver;
}


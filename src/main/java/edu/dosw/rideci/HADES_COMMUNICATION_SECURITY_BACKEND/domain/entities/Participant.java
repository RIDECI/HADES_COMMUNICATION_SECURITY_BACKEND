package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities;

import lombok.Data;

@Data
public class Participant {
    private String userId;
    private String name;
    private String avatarUrl;
    private boolean isDriver;
    private long unreadCount = 0;
}

package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.models;

import lombok.Data;

@Data
public class Participant {
    private String userId;
    private String name;
    private String avatarUrl;
    private boolean isDriver;
    private long unreadCount = 0;

    public Participant(String userId) {
        this.userId = userId;

    }
}

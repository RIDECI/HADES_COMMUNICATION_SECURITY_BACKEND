package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationVO {
    private double longitude;
    private double latitude;
    private String direction;
}

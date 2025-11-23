package edu.dosw.rideci.domain.valueobjects;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    private double longitude;
    private double latitude;
    private String direction;
}

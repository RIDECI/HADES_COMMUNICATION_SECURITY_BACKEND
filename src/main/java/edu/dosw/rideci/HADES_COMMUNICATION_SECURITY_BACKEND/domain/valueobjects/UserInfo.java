package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que maneka la información basica de  un usuario.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Long userId;
    private String name;
    private String email;
}

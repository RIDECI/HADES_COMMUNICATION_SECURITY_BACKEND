package edu.dosw.rideci.application.ports.out;



import edu.dosw.rideci.domain.entities.EmergencyAlert;

import java.util.List;
import java.util.Optional;

public interface EmergencyAlertRepositoryPort {
    EmergencyAlert save(EmergencyAlert e);
    Optional<EmergencyAlert> findById(String id);
    Optional<EmergencyAlert> findByUserId(String userId);
    List<EmergencyAlert> findAll();

}
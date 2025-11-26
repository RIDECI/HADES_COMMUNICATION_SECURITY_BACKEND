package edu.dosw.rideci.infrastructure.repositories;


import edu.dosw.rideci.domain.entities.EmergencyAlert;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmergencyAlertRepository extends MongoRepository<EmergencyAlert, String> {

    Optional<EmergencyAlert> findByUserId(String userId);
}
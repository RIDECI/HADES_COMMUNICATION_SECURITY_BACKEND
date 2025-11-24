package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.repositories;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.EmergencyAlert;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmergencyAlertRepository extends MongoRepository<EmergencyAlert, String> {

    Optional<EmergencyAlert> findByUserId(String userId);
}

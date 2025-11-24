package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.persistance;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out.EmergencyAlertRepositoryPort;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.EmergencyAlert;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.repositories.EmergencyAlertRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmergencyAlertRepositoryAdapter implements EmergencyAlertRepositoryPort {

    private final EmergencyAlertRepository emergencyAlertRepository;

    public EmergencyAlertRepositoryAdapter(EmergencyAlertRepository emergencyAlertRepository) {
        this.emergencyAlertRepository = emergencyAlertRepository;
    }

    @Override
    public EmergencyAlert save(EmergencyAlert alert) {
        return emergencyAlertRepository.save(alert);
    }

    @Override
    public Optional<EmergencyAlert> findById(String id) {
        return emergencyAlertRepository.findById(id);
    }

    @Override
    public Optional<EmergencyAlert> findByUserId(String userId) {
        return emergencyAlertRepository.findByUserId(userId);
    }

    @Override
    public List<EmergencyAlert> findAll() {
        return emergencyAlertRepository.findAll();
    }
}

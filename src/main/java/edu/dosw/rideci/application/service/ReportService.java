package edu.dosw.rideci.application.service;

import edu.dosw.rideci.application.dtos.request.AutomaticReportRequest;
import edu.dosw.rideci.application.dtos.request.EmergencyReportRequest;
import edu.dosw.rideci.application.dtos.request.ManualReportRequest;
import edu.dosw.rideci.application.dtos.response.ReportResponse;
import edu.dosw.rideci.application.events.ReportCreatedEvent;
import edu.dosw.rideci.application.mappers.ReportMapper;
import edu.dosw.rideci.application.ports.in.ActivateEmergencyButtonUseCase;
import edu.dosw.rideci.application.ports.in.CreateManualReportUseCase;
import edu.dosw.rideci.application.ports.in.DeviationDetectedUseCase;
import edu.dosw.rideci.application.ports.out.EventPublisher;
import edu.dosw.rideci.application.ports.out.ReportRepositoryPort;
import edu.dosw.rideci.domain.entities.Report;
import edu.dosw.rideci.domain.enums.ReportStatus;
import edu.dosw.rideci.domain.enums.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService implements
        DeviationDetectedUseCase,
        CreateManualReportUseCase,
        ActivateEmergencyButtonUseCase {

    private final ReportRepositoryPort reportRepo;
    private final ReportMapper mapper;
    private final EventPublisher eventPublisher;


    @Override
    @Transactional
    public void deviationDetected(Long userId, edu.dosw.rideci.domain.valueobjects.Location location, Long tripId, Long targetId) {

        AutomaticReportRequest dto = new AutomaticReportRequest();
        dto.setUserId(userId);
        dto.setTripId(tripId);
        dto.setCurrentLocation(location);
        dto.setTargetId(targetId);

        Report report = mapper.toAutomaticEntity(dto);
        report.setStatus(ReportStatus.PENDING);

        reportRepo.save(report);

        publishEvent(report);
    }


    @Override
    @Transactional
    public void createManual(Long userId, Long targetId, edu.dosw.rideci.domain.valueobjects.Location location, String description, Long tripId) {

        ManualReportRequest dto = new ManualReportRequest();
        dto.setUserId(userId);
        dto.setTargetId(targetId);
        dto.setLocation(location);
        dto.setDescription(description);
        dto.setTripId(tripId);

        Report report = mapper.toManualEntity(dto);
        report.setStatus(ReportStatus.PENDING);

        reportRepo.save(report);

        publishEvent(report);
    }


    @Override
    @Transactional
    public void activate(Long userId, Long tripId, edu.dosw.rideci.domain.valueobjects.Location currentLocation) {

        EmergencyReportRequest dto = new EmergencyReportRequest();
        dto.setUserId(userId);
        dto.setTripId(tripId);
        dto.setLocation(currentLocation);

        Report report = mapper.toEmergencyEntity(dto);
        report.setStatus(ReportStatus.PENDING);

        reportRepo.save(report);

        publishEvent(report);
    }

    private void publishEvent(Report report) {
        ReportCreatedEvent event = ReportCreatedEvent.builder()
                .reportId(report.getId())
                .userId(report.getUserId())
                .targetId(report.getTargetId())
                .tripId(report.getTripId())
                .type(report.getType())
                .location(report.getLocation())
                .description(report.getDescription())
                .createdAt(report.getCreatedAt())
                .status(report.getStatus())
                .evidence(report.getEvidence())
                .build();

        eventPublisher.publish(
                event,
                edu.dosw.rideci.infrastructure.config.RabbitMQConfig.REPORT_EXCHANGE,
                edu.dosw.rideci.infrastructure.config.RabbitMQConfig.REPORT_CREATED_ROUTING_KEY
        );
    }


    public List<ReportResponse> getReportsByUser(Long userId) {
        return reportRepo.findByUserId(userId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<ReportResponse> getReportsByTrip(Long tripId) {
        return reportRepo.findByTripId(tripId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<ReportResponse> getReportsByType(ReportType type) {
        return reportRepo.findByType(type)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<ReportResponse> getReportsByStatus(ReportStatus status) {
        return reportRepo.findByStatus(status)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<ReportResponse> getAllReports() {
        return reportRepo.findAllReports()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}

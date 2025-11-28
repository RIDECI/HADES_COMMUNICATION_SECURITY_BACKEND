package edu.dosw.rideci.application.service;

import edu.dosw.rideci.application.dtos.response.ReportResponse;
import edu.dosw.rideci.application.events.ReportCreatedEvent;
import edu.dosw.rideci.application.mappers.ReportMapper;
import edu.dosw.rideci.application.ports.in.ActivateEmergencyButtonUseCase;
import edu.dosw.rideci.application.ports.in.CreateAutomaticReportUseCase;
import edu.dosw.rideci.application.ports.in.CreateManualReportUseCase;
import edu.dosw.rideci.application.ports.out.EventPublisher;
import edu.dosw.rideci.application.ports.out.ReportRepositoryPort;
import edu.dosw.rideci.domain.entities.Report;
import edu.dosw.rideci.domain.enums.ReportType;
import edu.dosw.rideci.domain.valueobjects.Location;

import edu.dosw.rideci.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService implements
        CreateAutomaticReportUseCase,
        CreateManualReportUseCase,
        ActivateEmergencyButtonUseCase {

    private final ReportRepositoryPort reportRepo;
    private final ReportMapper mapper;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public void createAutomatic(Long userId, Location location, Long tripId, Long targetId) {
        Report report = Report.builder()
                .userId(userId)
                .location(location)
                .description("route deviation")
                .tripId(tripId)
                .type(ReportType.AUTOMATIC)
                .createdAt(LocalDateTime.now())
                .build();

        reportRepo.save(report);


        ReportCreatedEvent event = ReportCreatedEvent.builder()
                .reportId(report.getId())
                .userId(userId)
                .tripId(tripId)
                .targetId(targetId)
                .type(ReportType.AUTOMATIC)
                .location(location)
                .description("route deviation")
                .createdAt(report.getCreatedAt())
                .build();

        eventPublisher.publish(
                event,
                RabbitMQConfig.REPORT_EXCHANGE,
                RabbitMQConfig.REPORT_CREATED_ROUTING_KEY
        );
    }

    @Override
    @Transactional
    public void createManual(Long userId, Long targetId, Location location, String description, Long tripId) {
        Report report = Report.builder()
                .userId(userId)
                .targetId(targetId)
                .location(location)
                .description(description)
                .tripId(tripId)
                .type(ReportType.MANUAL)
                .createdAt(LocalDateTime.now())
                .build();

        reportRepo.save(report);


        ReportCreatedEvent event = ReportCreatedEvent.builder()
                .reportId(report.getId())
                .userId(userId)
                .tripId(tripId)
                .targetId(targetId)
                .type(ReportType.MANUAL)
                .location(location)
                .description(description)
                .createdAt(report.getCreatedAt())
                .build();

        eventPublisher.publish(
                event,
                RabbitMQConfig.REPORT_EXCHANGE,
                RabbitMQConfig.REPORT_CREATED_ROUTING_KEY
        );
    }


    // EMERGENCY BUTTON
    @Override
    @Transactional
    public void activate(Long userId, Long tripId, Location currentLocation) {
        Report report = Report.builder()
                .userId(userId)
                .tripId(tripId)
                .location(currentLocation)
                .description("Emergency button activated")
                .type(ReportType.EMERGENCY)
                .createdAt(LocalDateTime.now())
                .build();

        reportRepo.save(report);

        // Publicar evento
        ReportCreatedEvent event = ReportCreatedEvent.builder()
                .reportId(report.getId())
                .userId(userId)
                .tripId(tripId)
                .type(ReportType.EMERGENCY)
                .location(currentLocation)
                .description("Emergency button activated")
                .createdAt(report.getCreatedAt())
                .build();

        eventPublisher.publish(
                event,
                RabbitMQConfig.REPORT_EXCHANGE,
                RabbitMQConfig.REPORT_CREATED_ROUTING_KEY
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
}

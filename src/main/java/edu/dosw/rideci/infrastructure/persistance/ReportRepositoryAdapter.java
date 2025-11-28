package edu.dosw.rideci.infrastructure.persistance;


import edu.dosw.rideci.application.ports.out.ReportRepositoryPort;
import edu.dosw.rideci.domain.entities.Report;

import edu.dosw.rideci.domain.enums.ReportType;
import edu.dosw.rideci.infrastructure.repositories.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor

public class ReportRepositoryAdapter implements ReportRepositoryPort {

    private final ReportRepository repository;

    @Override
    public Report save(Report report) {
        return repository.save(report);
    }

    @Override
    public List<Report> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Report> findByType(ReportType type) {
        return repository.findByType(type);
    }

    @Override
    public List<Report> findAllReports() {
        return repository.findAll();
    }

    @Override
    public List<Report> findByTripId(Long tripId) {
        return repository.findByTripId(tripId);
    }
}



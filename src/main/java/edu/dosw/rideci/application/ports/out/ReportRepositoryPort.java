package edu.dosw.rideci.application.ports.out;

import edu.dosw.rideci.domain.entities.Report;

import edu.dosw.rideci.domain.enums.ReportType;

import java.util.List;

public interface ReportRepositoryPort {
    Report save(Report report);

    List<Report> findByUserId(Long userId);

    List<Report> findByTripId(Long tripId); 

    List<Report> findByType(ReportType type);

    List<Report> findAllReports();
}

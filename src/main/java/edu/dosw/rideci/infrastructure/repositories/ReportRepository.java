package edu.dosw.rideci.infrastructure.repositories;


import edu.dosw.rideci.domain.entities.Report;
import edu.dosw.rideci.domain.enums.ReportType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<Report, String> {
    List<Report> findByUserId(Long userId);
    List<Report> findByTripId(Long tripId);
    List<Report> findByType(ReportType type);
}

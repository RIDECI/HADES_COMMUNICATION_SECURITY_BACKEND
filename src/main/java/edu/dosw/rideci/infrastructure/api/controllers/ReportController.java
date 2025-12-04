package edu.dosw.rideci.infrastructure.api.controllers;

import edu.dosw.rideci.application.dtos.request.AutomaticReportRequest;
import edu.dosw.rideci.application.dtos.request.EmergencyReportRequest;
import edu.dosw.rideci.application.dtos.request.ManualReportRequest;
import edu.dosw.rideci.application.dtos.response.ReportResponse;
import edu.dosw.rideci.application.service.ReportService;
import edu.dosw.rideci.domain.enums.ReportStatus;
import edu.dosw.rideci.domain.enums.ReportType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "Report API", description = "Operations to manage user and trip reports")
public class ReportController {

    private final ReportService reportService;

    @Operation(
            summary = "Create a manual report",
            description = "Create a report manually from one user to another. 'targetId' and 'description' are required."
    )
    @PostMapping("/manual")
    public void createManual(@RequestBody ManualReportRequest request) {
        reportService.createManual(
                request.getUserId(),
                request.getTargetId(),
                request.getLocation(),
                request.getDescription(),
                request.getTripId()
        );
    }

    @Operation(
            summary = "Create an automatic report",
            description = "Create a system-generated automatic report (detour or location-based event)."
    )
    @PostMapping("/detour")
    public void createAutomatic(@RequestBody AutomaticReportRequest request) {
        reportService.deviationDetected(
                request.getUserId(),
                request.getCurrentLocation(),
                request.getTripId(),
                request.getTargetId()
        );
    }

    @Operation(
            summary = "Activate emergency button",
            description = "Create an EMERGENCY type report for a specific trip."
    )
    @PostMapping("/emergency")
    public void activateEmergency(@RequestBody EmergencyReportRequest request) {
        reportService.activate(
                request.getUserId(),
                request.getTripId(),
                request.getLocation()
        );
    }


    @Operation(summary = "Get reports by user", description = "Return all reports created by a specific user.")
    @GetMapping("/user/{userId}")
    public List<ReportResponse> getByUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId) {
        return reportService.getReportsByUser(userId);
    }

    @Operation(summary = "Get reports by trip", description = "Return all reports associated with a specific trip.")
    @GetMapping("/trip/{tripId}")
    public List<ReportResponse> getByTrip(
            @Parameter(description = "Trip ID", required = true) @PathVariable Long tripId) {
        return reportService.getReportsByTrip(tripId);
    }

    @Operation(
            summary = "Get reports by type",
            description = "Return all reports of a specific type: EMERGENCY, MANUAL, AUTOMATIC, DETOUR."
    )
    @GetMapping("/type/{type}")
    public List<ReportResponse> getByType(
            @Parameter(description = "Report type", required = true)
            @PathVariable ReportType type) {
        return reportService.getReportsByType(type);
    }

    @Operation(
            summary = "Get reports by status",
            description = "Return all reports with a specific status: PENDING, REVIEWED, CLOSED."
    )
    @GetMapping("/status/{status}")
    public List<ReportResponse> getByStatus(
            @Parameter(description = "Report status", required = true)
            @PathVariable ReportStatus status) {
        return reportService.getReportsByStatus(status);
    }

    @Operation(
            summary = "Get all reports",
            description = "Return all reports registered in the system."
    )
    @GetMapping
    public List<ReportResponse> getAll() {
        return reportService.getAllReports();
    }
}

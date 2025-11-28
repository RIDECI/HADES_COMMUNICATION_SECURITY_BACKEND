package edu.dosw.rideci.unit;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import edu.dosw.rideci.application.dtos.response.ReportResponse;
import edu.dosw.rideci.application.mappers.ReportMapper;
import edu.dosw.rideci.application.ports.out.ReportRepositoryPort;
import edu.dosw.rideci.application.service.ReportService;
import edu.dosw.rideci.infrastructure.config.RabbitMQConfig;
import edu.dosw.rideci.domain.entities.Report;
import edu.dosw.rideci.domain.enums.ReportType;
import edu.dosw.rideci.domain.valueobjects.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class ReportServiceTest {

    @Mock
    private ReportRepositoryPort reportRepo;

    @Mock
    private ReportMapper mapper;

    @Mock
    private edu.dosw.rideci.application.ports.out.EventPublisher eventPublisher;

    @InjectMocks
    private ReportService service;

    private Location location;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        location = new Location(4.123, -74.123,"hola"); // ejemplo de coordenadas
    }

    @Test
    void testCreateAutomaticReport() {
        service.createAutomatic(1L, location, 100L, 200L);

        ArgumentCaptor<Report> captor = ArgumentCaptor.forClass(Report.class);
        verify(reportRepo, times(1)).save(captor.capture());

        Report saved = captor.getValue();
        assertEquals(1L, saved.getUserId());
        assertEquals(ReportType.AUTOMATIC, saved.getType());
        assertEquals(location, saved.getLocation());
        assertEquals("route deviation", saved.getDescription());
    }

    @Test
    void testCreateManualReport() {
        String desc = "Manual report description";

        service.createManual(2L, 50L, location, desc, 101L);

        ArgumentCaptor<Report> captor = ArgumentCaptor.forClass(Report.class);
        verify(reportRepo, times(1)).save(captor.capture());

        Report saved = captor.getValue();
        assertEquals(2L, saved.getUserId());
        assertEquals(50L, saved.getTargetId());
        assertEquals(ReportType.MANUAL, saved.getType());
        assertEquals(desc, saved.getDescription());
        assertEquals(location, saved.getLocation());
    }

    @Test
    void testActivateEmergencyButton() {
        service.activate(3L, 150L, location);

        ArgumentCaptor<Report> captor = ArgumentCaptor.forClass(Report.class);
        verify(reportRepo, times(1)).save(captor.capture());

        Report saved = captor.getValue();
        assertEquals(3L, saved.getUserId());
        assertEquals(150L, saved.getTripId());
        assertEquals(ReportType.EMERGENCY, saved.getType());
        assertEquals("Emergency button activated", saved.getDescription());
        assertEquals(location, saved.getLocation());
    }

    @Test
    void testGetReportsByUser() {
        Report report1 = Report.builder().userId(1L).type(ReportType.AUTOMATIC).build();
        Report report2 = Report.builder().userId(1L).type(ReportType.MANUAL).build();

        when(reportRepo.findByUserId(1L)).thenReturn(List.of(report1, report2));
        when(mapper.toDTO(any())).thenReturn(new ReportResponse());

        List<ReportResponse> results = service.getReportsByUser(1L);
        assertEquals(2, results.size());
        verify(reportRepo, times(1)).findByUserId(1L);
    }

    @Test
    void testGetReportsByTrip() {
        Report report = Report.builder().tripId(100L).type(ReportType.AUTOMATIC).build();

        when(reportRepo.findByTripId(100L)).thenReturn(List.of(report));
        when(mapper.toDTO(any())).thenReturn(new ReportResponse());

        List<ReportResponse> results = service.getReportsByTrip(100L);
        assertEquals(1, results.size());
        verify(reportRepo, times(1)).findByTripId(100L);
    }

    @Test
    void testGetReportsByType() {
        Report report = Report.builder().type(ReportType.EMERGENCY).build();

        when(reportRepo.findByType(ReportType.EMERGENCY)).thenReturn(List.of(report));
        when(mapper.toDTO(any())).thenReturn(new ReportResponse());

        List<ReportResponse> results = service.getReportsByType(ReportType.EMERGENCY);
        assertEquals(1, results.size());
        verify(reportRepo, times(1)).findByType(ReportType.EMERGENCY);
    }
}

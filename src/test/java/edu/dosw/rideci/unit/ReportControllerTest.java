package edu.dosw.rideci.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.rideci.application.dtos.request.AutomaticReportRequest;
import edu.dosw.rideci.application.dtos.request.EmergencyReportRequest;
import edu.dosw.rideci.application.dtos.request.ManualReportRequest;
import edu.dosw.rideci.application.dtos.response.ReportResponse;
import edu.dosw.rideci.application.service.ReportService;
import edu.dosw.rideci.domain.enums.ReportType;
import edu.dosw.rideci.infrastructure.api.controllers.ReportController;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Autowired
    private ObjectMapper mapper;

    // -------------------------------
    // TEST CREATE MANUAL REPORT
    // -------------------------------
    @Test
    void testCreateManualReport() throws Exception {

        ManualReportRequest req = new ManualReportRequest();
        req.setUserId(1001L);
        req.setTargetId(2001L);
        req.setDescription("User reported another user");
        req.setTripId(50L);
        req.setLocation(new edu.dosw.rideci.domain.valueobjects.Location(10.0, 20.0, "N"));

        // No need to mock return for void method

        String json = mapper.writeValueAsString(req);

        mockMvc.perform(post("/reports/manual")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    // -------------------------------
    // TEST CREATE AUTOMATIC REPORT
    // -------------------------------
    @Test
    void testCreateAutomaticReport() throws Exception {

        AutomaticReportRequest req = new AutomaticReportRequest();
        req.setUserId(1002L);

        req.setTripId(51L);
        req.setLocation(new edu.dosw.rideci.domain.valueobjects.Location(30.0, 40.0, "E"));

        String json = mapper.writeValueAsString(req);

        mockMvc.perform(post("/reports/automatic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    // -------------------------------
    // TEST EMERGENCY BUTTON REPORT
    // -------------------------------
    @Test
    void testActivateEmergencyReport() throws Exception {

        EmergencyReportRequest req = new EmergencyReportRequest();
        req.setUserId(1003L);
        req.setTripId(52L);
        req.setLocation(new edu.dosw.rideci.domain.valueobjects.Location(50.0, 60.0, "S"));

        String json = mapper.writeValueAsString(req);

        mockMvc.perform(post("/reports/emergency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    // -------------------------------
    // TEST GET REPORTS BY USER
    // -------------------------------
    @Test
    void testGetReportsByUser() throws Exception {

        ReportResponse r1 = new ReportResponse();
        r1.setId("r1");
        r1.setUserId(1001L);
        r1.setType(ReportType.MANUAL);
        r1.setCreatedAt(LocalDateTime.now());

        ReportResponse r2 = new ReportResponse();
        r2.setId("r2");
        r2.setUserId(1001L);
        r2.setType(ReportType.AUTOMATIC);
        r2.setCreatedAt(LocalDateTime.now());

        Mockito.when(reportService.getReportsByUser(1001L))
                .thenReturn(List.of(r1, r2));

        String result = mockMvc.perform(get("/reports/user/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("r1"))
                .andExpect(jsonPath("$[1].id").value("r2"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<?> list = mapper.readValue(result, List.class);
        assertEquals(2, list.size());
    }

    // -------------------------------
    // TEST GET REPORTS BY TRIP
    // -------------------------------
    @Test
    void testGetReportsByTrip() throws Exception {

        ReportResponse r = new ReportResponse();
        r.setId("r3");
        r.setTripId(55L);
        r.setType(ReportType.EMERGENCY);

        Mockito.when(reportService.getReportsByTrip(55L))
                .thenReturn(List.of(r));

        String result = mockMvc.perform(get("/reports/trip/55"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("r3"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<?> list = mapper.readValue(result, List.class);
        assertEquals(1, list.size());
    }

    // -------------------------------
    // TEST GET REPORTS BY TYPE
    // -------------------------------
    @Test
    void testGetReportsByType() throws Exception {

        ReportResponse r = new ReportResponse();
        r.setId("r4");
        r.setType(ReportType.AUTOMATIC);

        Mockito.when(reportService.getReportsByType(ReportType.AUTOMATIC))
                .thenReturn(List.of(r));

        String result = mockMvc.perform(get("/reports/type/AUTOMATIC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("r4"))
                .andExpect(jsonPath("$[0].type").value("AUTOMATIC"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<?> list = mapper.readValue(result, List.class);
        assertEquals(1, list.size());
    }
}

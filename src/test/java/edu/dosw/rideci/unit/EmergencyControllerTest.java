package edu.dosw.rideci.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.rideci.application.dtos.request.EmergencyAlertRequest;
import edu.dosw.rideci.application.dtos.response.EmergencyAlertResponse;
import edu.dosw.rideci.application.service.EmergencyAlertService;
import edu.dosw.rideci.domain.valueobjects.Location;
import edu.dosw.rideci.infrastructure.api.controllers.EmergencyAlertController;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmergencyAlertController.class)
class EmergencyAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmergencyAlertService emergencyAlertService;

    @Autowired
    private ObjectMapper objectMapper;


    @SuppressWarnings("null")
    @Test
    void testShouldActivateEmergency() throws Exception {

        EmergencyAlertRequest req = new EmergencyAlertRequest();
        req.setUserId(10L);
        req.setTravelId(50L);
        req.setCurrentLocation(
                new Location(1.234, 5.678, "Cra 123 #456")
        );

        Mockito.doNothing().when(emergencyAlertService)
                .activate(anyLong(), anyLong(), any(Location.class));

        mockMvc.perform(
                        post("/emergencies/activate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Emergency alert activated"));
    }


    @Test
    void testShouldGetAlertFound() throws Exception {

        EmergencyAlertResponse resp = new EmergencyAlertResponse();
        resp.setId("alert123");
        resp.setUserId(10L);
        resp.setTripId(50L);
        resp.setCurrentLocation(new Location(1.2, 3.4, "Street A"));
        resp.setCreatedAt(LocalDateTime.now());
        resp.setAdditionalInfo("Emergency button activated");

        Mockito.when(emergencyAlertService.getAlert("alert123"))
                .thenReturn(resp);

        mockMvc.perform(get("/emergencies/alert123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("alert123"))
                .andExpect(jsonPath("$.userId").value(10))
                .andExpect(jsonPath("$.tripId").value(50))
                .andExpect(jsonPath("$.currentLocation.latitude").value(3.4));
    }

    @Test
    void tesShouldNottGetAlert() throws Exception {

        Mockito.when(emergencyAlertService.getAlert("notFound"))
                .thenReturn(null);

        mockMvc.perform(get("/emergencies/notFound"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testShouldGetAllAlerts() throws Exception {

        EmergencyAlertResponse a1 = new EmergencyAlertResponse();
        a1.setId("a1");
        a1.setUserId(1L);
        a1.setTripId(100L);
        a1.setCurrentLocation(new Location(1, 2, "Dir A"));

        EmergencyAlertResponse a2 = new EmergencyAlertResponse();
        a2.setId("a2");
        a2.setUserId(2L);
        a2.setTripId(200L);
        a2.setCurrentLocation(new Location(3, 4, "Dir B"));

        Mockito.when(emergencyAlertService.getAllAlerts())
                .thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/emergencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("a1"))
                .andExpect(jsonPath("$[1].id").value("a2"))
                .andExpect(jsonPath("$[0].currentLocation.latitude").value(2.0))
                .andExpect(jsonPath("$[1].currentLocation.latitude").value(4.0));
    }
}

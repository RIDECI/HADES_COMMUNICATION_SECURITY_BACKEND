package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.api.controllers;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.request.EmergencyAlertRequest;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response.EmergencyAlertResponse;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.service.EmergencyAlertService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing emergency alerts.
 *
 * Provides endpoints to activate an emergency alert and
 * retrieve existing alerts by ID.
 *
 * Base path: /emergencies
 */
@AllArgsConstructor
@RestController
@RequestMapping("/emergencies")
public class EmergencyAlertController {

    /**
     * Service that contains the business logic for emergency alerts.
     */
    private final EmergencyAlertService emergencyAlertService;

    /**
     * Activates an emergency alert.
     *
     * This endpoint receives an {@link EmergencyAlertRequest} object containing:
     * <ul>
     *     <li>userId: ID of the user triggering the alert.</li>
     *     <li>tripId: ID of the associated trip.</li>
     *     <li>currentLocation: current location of the user.</li>
     * </ul>
     *
     * Invoking this endpoint creates a new record in the database with the current timestamp.
     *
     * @param requestDTO DTO containing the emergency alert data.
     * @return ResponseEntity with confirmation message.
     */
    @Operation(summary = "Activate an emergency alert", description = "Creates a new emergency alert for a user during a trip with the current location.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emergency alert activated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/activate")
    public ResponseEntity<String> activateEmergency(@RequestBody EmergencyAlertRequest requestDTO) {

        emergencyAlertService.activate(
                requestDTO.getUserId(),
                requestDTO.getTripId(),
                requestDTO.getCurrentLocation()
        );

        return ResponseEntity.ok("Emergency alert activated");
    }

    /**
     * Retrieves an emergency alert by its ID.
     *
     * This endpoint returns an {@link EmergencyAlertResponse} object containing:
     * <ul>
     *     <li>id: Alert ID.</li>
     *     <li>userId: User ID.</li>
     *     <li>tripId: Trip ID.</li>
     *     <li>currentLocation: Current location of the alert.</li>
     *     <li>createdAt: Timestamp when the alert was created.</li>
     *     <li>additionalInfo: Additional information about the alert.</li>
     * </ul>
     *
     * @param id ID of the emergency alert.
     * @return ResponseEntity with the alert DTO or 404 Not Found if the alert does not exist.
     */
    @Operation(summary = "Get an emergency alert by ID", description = "Retrieves an existing emergency alert by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emergency alert retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Emergency alert not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmergencyAlertResponse> getAlert(@PathVariable String id) {

        EmergencyAlertResponse responseDTO = emergencyAlertService.getAlert(id);

        if (responseDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(responseDTO);

    }
    /**
     * Retrieves all emergency alerts.
     *
     * This endpoint returns a list of {@link EmergencyAlertResponse} objects,
     * each containing:
     * <ul>
     *     <li>id: Alert ID.</li>
     *     <li>userId: User ID.</li>
     *     <li>tripId: Trip ID.</li>
     *     <li>currentLocation: Current location of the alert.</li>
     *     <li>createdAt: Timestamp when the alert was created.</li>
     *     <li>additionalInfo: Additional information about the alert.</li>
     * </ul>
     *
     * @return ResponseEntity with the list of all emergency alerts.
     */
    @Operation(summary = "Get all emergency alerts", description = "Retrieves a list of all existing emergency alerts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emergency alerts retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<EmergencyAlertResponse>> getAllAlerts() {
        List<EmergencyAlertResponse> alerts = emergencyAlertService.getAllAlerts();
        return ResponseEntity.ok(alerts);
    }
}

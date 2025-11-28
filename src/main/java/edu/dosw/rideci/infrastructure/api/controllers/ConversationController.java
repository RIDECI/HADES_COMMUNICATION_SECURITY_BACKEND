package edu.dosw.rideci.infrastructure.api.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.dosw.rideci.application.dtos.request.CreateConversationRequest;
import edu.dosw.rideci.application.dtos.request.SendMessageRequest;
import edu.dosw.rideci.application.dtos.response.ConversationResponse;
import edu.dosw.rideci.application.dtos.response.MessageResponse;
import edu.dosw.rideci.application.events.command.CreateConversationCommand;
import edu.dosw.rideci.application.service.ConversationService;
import edu.dosw.rideci.domain.entities.Message;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationService service;

    public ConversationController(ConversationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ConversationResponse> create(@RequestBody CreateConversationRequest req) {

        CreateConversationCommand command = CreateConversationCommand.builder()
                .tripId(req.getTripId())
                .chatType(req.getType())
                .participants(req.getParticipants())
                .driverId(req.getDriverId())
                .organizerId(req.getOrganizerId())
                .travelStatus(req.getTravelStatus())
                .build();

        String conversationId = service.createChat(command);

        ConversationResponse resp = service.getConversation(conversationId);

        return ResponseEntity.ok(resp);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ConversationResponse> getConversation(@PathVariable String id) {
        return ResponseEntity.ok(service.getConversation(id));
    }
    
    @GetMapping("/{id}/messages")
    public ResponseEntity<List<MessageResponse>> messages(@PathVariable String id) {
        return ResponseEntity.ok(service.getMessages(id));
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity<MessageResponse> sendMessage(@PathVariable String id, @RequestBody SendMessageRequest req) {

        Message message = new Message(id,req.getSenderId(),req.getContent());

        service.sendMessage(id, message);

        MessageResponse resp = service.toMessageResponse(message);

        return ResponseEntity.ok(resp);
    }
}
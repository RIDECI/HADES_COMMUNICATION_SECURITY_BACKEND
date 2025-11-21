package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.request.CreateConversationRequest;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.request.ParticipantRequest;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.request.SendMessageRequest;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response.ConversationResponse;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response.MessageResponse;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.service.ConversationService;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.Message;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationService service;

    public ConversationController(ConversationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ConversationResponse> create(@RequestBody CreateConversationRequest req) {

        List<String> participantIds = req.getParticipants()
                .stream()
                .map(ParticipantRequest::getUserId)
                .toList();

        String conversationId =
                service.createChat(participantIds, req.getType(), req.getTripId());

        ConversationResponse resp = service.getConversation(conversationId);

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<List<MessageResponse>> messages(@PathVariable String id) {
        return ResponseEntity.ok(service.getMessages(id));
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity<MessageResponse> sendMessage(
            @PathVariable String id,
            @RequestBody SendMessageRequest req) {

        Message message = new Message(
                id,
                req.getSenderId(),
                req.getContent()
        );

        service.sendMessage(id, message);

        MessageResponse resp = service.toMessageResponse(message);

        return ResponseEntity.ok(resp);
    }
}

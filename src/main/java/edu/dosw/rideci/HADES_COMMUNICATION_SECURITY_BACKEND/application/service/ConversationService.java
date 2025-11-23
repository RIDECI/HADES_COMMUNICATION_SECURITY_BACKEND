package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in.CreateConversationUseCase;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in.SendMessageUseCase;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in.UpdateConversationStatusUseCase;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out.ConversationRepositoryPort;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out.MessageRepositoryPort;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out.EventPublisher;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.infrastructure.config.RabbitMQConfig;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.*;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.TravelStatus;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.models.Participant;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response.ConversationResponse;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response.MessageResponse;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.ConversationCreatedEvent;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.MessageSentEvent;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.TripFinishEvent;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.command.CreateConversationCommand;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.exceptions.ConversationException;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.mappers.ConversationMapper;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService implements CreateConversationUseCase, SendMessageUseCase, UpdateConversationStatusUseCase {

    private final ConversationRepositoryPort convRepo;
    private final MessageRepositoryPort msgRepo;
    private final ConversationMapper mapper;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public String createChat(CreateConversationCommand command) {
        List<Participant> participants = command.getParticipants()
                .stream()
                .map(Participant::new)
                .toList();

        Conversation conv = new Conversation();
        conv.setTripId(command.getTripId());
        conv.setType(command.getChatType());
        conv.setParticipants(participants);

        TravelStatus travelStatus = command.getTravelStatus();
        conv.setTravelStatus(travelStatus);

        boolean isActive = (travelStatus == TravelStatus.IN_COURSE || travelStatus == TravelStatus.ACTIVE);
        conv.setActive(isActive);

        convRepo.save(conv);

        ConversationCreatedEvent event = ConversationCreatedEvent.builder()
                .conversationId(conv.getId())
                .tripId(conv.getTripId())
                .participants(command.getParticipants())
                .type(conv.getType().name())
                .createdAt(LocalDateTime.now())
                .build();

        eventPublisher.publish(
                event,
                RabbitMQConfig.CHAT_EXCHANGE,
                "conversation.created"
        );

        return conv.getId();
    }

    @Override
    @Transactional
    public void sendMessage(String conversationId, Message message) {
        if (!convRepo.existsById(conversationId)) {
            throw new ConversationException("Conversation no encontrada");
        }

        msgRepo.save(message);

        MessageSentEvent event = MessageSentEvent.builder()
                .conversationId(conversationId)
                .messageId(message.getMessageId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .sentAt(message.getTimestamp()
                        .toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();

        eventPublisher.publish(
                event,
                RabbitMQConfig.CHAT_EXCHANGE,
                "chat.message"
        );
    }

    public List<MessageResponse> getMessages(String conversationId) {
        if (!convRepo.existsById(conversationId)) {
            throw new ConversationException("Conversation no encontrada");
        }
        return msgRepo.findByConversationId(conversationId)
                .stream()
                .map(mapper::toMessageResponse)
                .toList();
    }

    public ConversationResponse getConversation(String conversationId) {
        return convRepo.findById(conversationId)
                .map(mapper::toConversationResponse)
                .orElseThrow(() -> new ConversationException("Conversation no encontrada"));
    }

    public MessageResponse toMessageResponse(Message message) {
        return mapper.toMessageResponse(message);
    }

    @Transactional
    public void updateStatus(Long tripId, TravelStatus status) {
        Conversation conv = convRepo.findByTripId(tripId)
                .orElseThrow(() -> new ConversationException(
                        "No existe conversación para el tripId: " + tripId));

        boolean isActive = (status == TravelStatus.IN_COURSE || status == TravelStatus.ACTIVE);
        conv.setActive(isActive);
        conv.setTravelStatus(status);
        conv.setUpdatedAt(new Date());

        convRepo.save(conv);

        if (status == TravelStatus.COMPLETED) {
            TripFinishEvent event = TripFinishEvent.builder()
                    .id(tripId)
                    .travelStatus(status)
                    .build();

           
            eventPublisher.publish(
                    event,
                    RabbitMQConfig.TRIP_EXCHANGE,
                    RabbitMQConfig.TRIP_FINISHED_ROUTING_KEY 
            );
        }
    }
}

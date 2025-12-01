package edu.dosw.rideci.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.dosw.rideci.application.ports.in.CreateConversationUseCase;
import edu.dosw.rideci.application.ports.in.SendMessageUseCase;
import edu.dosw.rideci.application.ports.in.UpdateConversationStatusUseCase;
import edu.dosw.rideci.application.ports.out.ConversationRepositoryPort;
import edu.dosw.rideci.application.ports.out.MessageRepositoryPort;
import edu.dosw.rideci.application.ports.out.EventPublisher;

import edu.dosw.rideci.infrastructure.config.RabbitMQConfig;

import edu.dosw.rideci.domain.entities.Conversation;
import edu.dosw.rideci.domain.entities.Message;
import edu.dosw.rideci.domain.enums.Status;
import edu.dosw.rideci.domain.enums.TravelType;

import edu.dosw.rideci.application.dtos.response.ConversationResponse;
import edu.dosw.rideci.application.dtos.response.MessageResponse;

import edu.dosw.rideci.application.events.ConversationCreatedEvent;
import edu.dosw.rideci.application.events.MessageSentEvent;
import edu.dosw.rideci.application.events.TravelCompletedEvent;

import edu.dosw.rideci.application.events.command.CreateConversationCommand;
import edu.dosw.rideci.application.exceptions.ConversationException;
import edu.dosw.rideci.application.mappers.ConversationMapper;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService
        implements CreateConversationUseCase, SendMessageUseCase, UpdateConversationStatusUseCase {

    private final ConversationRepositoryPort convRepo;
    private final MessageRepositoryPort msgRepo;
    private final ConversationMapper mapper;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public String createChat(CreateConversationCommand command) {

        Conversation conv = new Conversation();
        conv.setTravelId(command.getTravelId());
        conv.setType(command.getChatType());
        conv.setTravelStatus(command.getTravelStatus());
        conv.setOrganizerId(command.getOrganizerId());
        conv.setDriverId(command.getDriverId());

        boolean isActive = command.getTravelStatus() == Status.IN_COURSE
                || command.getTravelStatus() == Status.ACTIVE;

        conv.setActive(isActive);

        List<Long> finalParticipants = new java.util.ArrayList<>(command.getParticipants());

        if (command.getChatType() == TravelType.GROUP) {
            if (command.getOrganizerId() != null && !finalParticipants.contains(command.getOrganizerId())) {
                finalParticipants.add(command.getOrganizerId());
            }
        } else { 
            if (command.getDriverId() != null && !finalParticipants.contains(command.getDriverId())) {
                finalParticipants.add(command.getDriverId());
            }
        }

        conv.setParticipants(finalParticipants);
        convRepo.save(conv);

        ConversationCreatedEvent event = ConversationCreatedEvent.builder()
                .conversationId(conv.getId())
                .travelId(conv.getTravelId())
                .participants(finalParticipants)
                .type(conv.getType().name())
                .createdAt(LocalDateTime.now())
                .build();

        eventPublisher.publish(
                event,
                RabbitMQConfig.CONVERSATION_EXCHANGE,
                RabbitMQConfig.CONVERSATION_CREATED_ROUTING_KEY
        );

        return conv.getId();
    }


    @Override
    @Transactional
    public void sendMessage(String conversationId, Message message) {

        Conversation conv = convRepo.findById(conversationId)
                .orElseThrow(() -> new ConversationException("Conversation no encontrada"));

        if (!conv.isActive()) {
            throw new ConversationException("La conversación no está activa");
        }

        String senderId = message.getSenderId();
        String receiverId = message.getReceiverId();

        boolean senderIsParticipant = conv.getParticipants().stream()
                .anyMatch(p -> p.toString().equals(senderId));

        if (!senderIsParticipant) {
            throw new ConversationException("El remitente no pertenece a la conversación");
        }

        if (conv.getType() == TravelType.TRIP) {

            if (receiverId == null) {
                throw new ConversationException("receiverId es obligatorio para chats TRIP");
            }

            boolean receiverIsParticipant = conv.getParticipants().stream()
                    .anyMatch(p -> p.toString().equals(receiverId));

            if (!receiverIsParticipant) {
                throw new ConversationException("El receptor no pertenece a la conversación");
            }

            msgRepo.save(message);

            MessageSentEvent event = MessageSentEvent.builder()
                    .conversationId(conversationId)
                    .messageId(message.getMessageId())
                    .senderId(senderId)
                    .receiverId(receiverId)
                    .content(message.getContent())
                    .sentAt(message.getTimestamp().toInstant()
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDateTime())
                    .build();

            eventPublisher.publish(
                    event,
                    RabbitMQConfig.CHAT_EXCHANGE,
                    RabbitMQConfig.CHAT_ROUTING_KEY
            );

            return;
        }

        if (conv.getType() == TravelType.GROUP) {

            msgRepo.save(message);

            for (Long participant : conv.getParticipants()) {

                if (participant.toString().equals(senderId)) {
                    continue;
                }

                MessageSentEvent event = MessageSentEvent.builder()
                        .conversationId(conversationId)
                        .messageId(message.getMessageId())
                        .senderId(senderId)
                        .receiverId(participant.toString())
                        .content(message.getContent())
                        .sentAt(message.getTimestamp().toInstant()
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDateTime())
                        .build();

                eventPublisher.publish(
                        event,
                        RabbitMQConfig.CHAT_EXCHANGE,
                        RabbitMQConfig.CHAT_ROUTING_KEY
                );
            }
        }
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

    public List<ConversationResponse> getAllConversations() {
        return convRepo.findAll().stream()
                .map(mapper::toConversationResponse)
                .toList();
    }

    @Override
    @Transactional
    public void updateStatus(String tripId, Status status) {

        Conversation conv = convRepo.findByTravelId(tripId)
                .orElseThrow(() -> new ConversationException(
                        "No existe conversación para el tripId: " + tripId));

        boolean isActive = status == Status.IN_COURSE || status == Status.ACTIVE;
        conv.setActive(isActive);
        conv.setTravelStatus(status);
        conv.setUpdatedAt(new Date());

        convRepo.save(conv);

        if (status == Status.COMPLETED) {
            TravelCompletedEvent event = TravelCompletedEvent.builder()
                    .travelId(tripId)
                    .state(status)
                    .build();

            eventPublisher.publish(
                    event,
                    RabbitMQConfig.TRIP_EXCHANGE,
                    RabbitMQConfig.TRIP_FINISHED_ROUTING_KEY
            );
        }
    }

    public MessageResponse toMessageResponse(Message message) {
        return mapper.toMessageResponse(message);
    }
}

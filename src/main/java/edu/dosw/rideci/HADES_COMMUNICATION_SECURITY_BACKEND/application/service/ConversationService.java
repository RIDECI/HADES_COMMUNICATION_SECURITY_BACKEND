package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in.CreateConversationUseCase;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.in.SendMessageUseCase;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out.ConversationRepositoryPort;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out.MessageRepositoryPort;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.ports.out.EventPublisher;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.*;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.enums.ConversationType;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response.ConversationResponse;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response.MessageResponse;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.ConversationCreatedEvent;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.events.MessageSentEvent;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.exceptions.ConversationException;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.mappers.ConversationMapper;

import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService implements CreateConversationUseCase, SendMessageUseCase {

    private final ConversationRepositoryPort convRepo;
    private final MessageRepositoryPort msgRepo;
    private final ConversationMapper mapper;
    private final TripClient tripClient;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public String createChat(List<String> participantIds, ConversationType chatType, Long travelId) {
        if (travelId != null && !tripClient.tripExists(travelId)) {
            throw new ConversationException("Trip no válido o no existe");
        }

        List<Participant> participants = participantIds.stream()
                                                       .map(Participant::new)
                                                       .toList();

        Conversation conv = new Conversation();
        conv.setTripId(travelId);
        conv.setType(chatType);
        conv.setParticipants(participants);

        convRepo.save(conv);

        ConversationCreatedEvent event = ConversationCreatedEvent.builder()
                .conversationId(conv.getId())
                .tripId(conv.getTripId())
                .participants(participantIds)
                .type(conv.getType().name())
                .createdAt(LocalDateTime.now())
                .build();

        eventPublisher.publish(event, "conversation.created");

        return conv.getId();
    }

    @Override
    @Transactional
    public void sendMessage(String chatId, Message message) {
        if (!convRepo.existsById(chatId)) {
            throw new ConversationException("Conversation no encontrada");
        }

        msgRepo.save(message);

        MessageSentEvent msgEvent = MessageSentEvent.builder()
                .conversationId(chatId)
                .messageId(message.getMessageId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .sentAt(message.getTimestamp().toInstant()
                               .atZone(java.time.ZoneId.systemDefault())
                               .toLocalDateTime())
                .build();

        eventPublisher.publish(msgEvent, "message.sent");
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
}

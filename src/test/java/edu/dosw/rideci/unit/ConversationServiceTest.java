package edu.dosw.rideci.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import edu.dosw.rideci.application.dtos.response.MessageResponse;
import edu.dosw.rideci.application.dtos.response.ConversationResponse;
import edu.dosw.rideci.application.events.MessageSentEvent;
import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.application.events.command.CreateConversationCommand;
import edu.dosw.rideci.application.exceptions.ConversationException;
import edu.dosw.rideci.application.mappers.ConversationMapper;
import edu.dosw.rideci.application.ports.out.ConversationRepositoryPort;
import edu.dosw.rideci.application.ports.out.MessageRepositoryPort;
import edu.dosw.rideci.application.service.ConversationService;
import edu.dosw.rideci.application.ports.out.EventPublisher;
import edu.dosw.rideci.domain.entities.Conversation;
import edu.dosw.rideci.domain.entities.Message;
import edu.dosw.rideci.domain.enums.Status;
import edu.dosw.rideci.domain.enums.TravelType;
import edu.dosw.rideci.infrastructure.config.RabbitMQConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class ConversationServiceTest {

    @Mock
    private ConversationRepositoryPort convRepo;

    @Mock
    private MessageRepositoryPort msgRepo;

    @Mock
    private ConversationMapper mapper;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private ConversationService service;

    private Conversation conversation;

    @BeforeEach
    void setUp() {
        conversation = new Conversation();
        conversation.setId("abc123");
        conversation.setTripId(10L);
        conversation.setParticipants(List.of(1L, 2L));
        conversation.setType(TravelType.TRIP);
        conversation.setActive(true);
        conversation.setTravelStatus(Status.ACTIVE);
    }

    @Test
    void testShouldCreateChatWhenActive() {
        CreateConversationCommand cmd = mock(CreateConversationCommand.class);
        when(cmd.getTripId()).thenReturn(1L);
        when(cmd.getChatType()).thenReturn(TravelType.TRIP);
        when(cmd.getParticipants()).thenReturn(List.of(10L));
        when(cmd.getTravelStatus()).thenReturn(Status.ACTIVE);

        when(convRepo.save(any())).thenAnswer(invocation -> {
            Conversation c = invocation.getArgument(0);
            c.setId("1");
            return c;
        });

        String id = service.createChat(cmd);

        assertEquals("1", id);
    }

    @Test
    void testShouldcreateChatwhenInCourse() {
        CreateConversationCommand cmd = mock(CreateConversationCommand.class);
        when(cmd.getTripId()).thenReturn(1L);
        when(cmd.getChatType()).thenReturn(TravelType.TRIP);
        when(cmd.getParticipants()).thenReturn(List.of(10L));
        when(cmd.getTravelStatus()).thenReturn(Status.IN_COURSE);

        when(convRepo.save(any())).thenAnswer(invocation -> {
            Conversation c = invocation.getArgument(0);
            assertTrue(c.isActive());
            c.setId("1");
            return c;
        });

        service.createChat(cmd);
    }

    @Test
    void testShouldCreateChatWhenCompleted() {
        CreateConversationCommand cmd = mock(CreateConversationCommand.class);
        when(cmd.getTravelStatus()).thenReturn(Status.COMPLETED);
        when(cmd.getTripId()).thenReturn(1L);
        when(cmd.getChatType()).thenReturn(TravelType.TRIP);
        when(cmd.getParticipants()).thenReturn(List.of(10L));

        when(convRepo.save(any())).thenAnswer(invocation -> {
            Conversation c = invocation.getArgument(0);
            assertFalse(c.isActive());
            c.setId("1");
            return c;
        });

        service.createChat(cmd);
    }

    @Test
    void testShouldCreateChatWhenCancelled() {
        CreateConversationCommand cmd = mock(CreateConversationCommand.class);
        when(cmd.getTravelStatus()).thenReturn(Status.CANCELLED);
        when(cmd.getTripId()).thenReturn(1L);
        when(cmd.getChatType()).thenReturn(TravelType.TRIP);
        when(cmd.getParticipants()).thenReturn(List.of(10L));

        when(convRepo.save(any())).thenAnswer(invocation -> {
            Conversation c = invocation.getArgument(0);
            assertFalse(c.isActive());
            c.setId("1");
            return c;
        });

        service.createChat(cmd);
    }


    @Test
    void testShouldSendMessage() {
        when(convRepo.existsById("abc123")).thenReturn(true);
        Message message = new Message("abc123", "1", "Hola");

        service.sendMessage("abc123", message);

        verify(msgRepo, times(1)).save(message);
        verify(eventPublisher, times(1)).publish(
                any(MessageSentEvent.class),
                eq(RabbitMQConfig.CHAT_EXCHANGE),
                eq(RabbitMQConfig.CHAT_ROUTING_KEY)
        );
    }

    @Test
    void testShouldNotSendMessageConversationNotFound() {
        when(convRepo.existsById("404")).thenReturn(false);
        Message message = new Message("404", "1", "Hola");

        try {
            service.sendMessage("404", message);
            fail("Debió lanzar excepción");
        } catch (ConversationException ex) {
            assertTrue(ex.getMessage().toLowerCase().contains("no encontrada"));
        }
    }

    @Test
    void testShouldGetMessages() {
        when(convRepo.existsById("abc123")).thenReturn(true);

        when(msgRepo.findByConversationId("abc123"))
                .thenReturn(List.of(
                        new Message("abc123", "1", "Hola"),
                        new Message("abc123", "2", "Chao")
                ));

        when(mapper.toMessageResponse(any())).thenReturn(new MessageResponse());

        List<MessageResponse> result = service.getMessages("abc123");

        assertEquals(2, result.size());
    }

    @Test
    void testShouldGetMessagesNotFound_throwsException() {
        when(convRepo.existsById("missing")).thenReturn(false);

        ConversationException ex = assertThrows(ConversationException.class, () -> {
            service.getMessages("missing");
        });

        assertTrue(ex.getMessage().contains("Conversation no encontrada"));
    }

    @Test
    void testShouldGetConversation() {
        when(convRepo.findById("abc123")).thenReturn(Optional.of(conversation));
        when(mapper.toConversationResponse(any())).thenReturn(new ConversationResponse());

        ConversationResponse response = service.getConversation("abc123");

        assertNotNull(response);
    }

    @Test
    void testShouldGetConversationNotFound() {
        when(convRepo.findById(any())).thenReturn(Optional.empty());

        assertThrows(ConversationException.class, () -> service.getConversation("notFound"));
    }

    @Test
    void testShouldUpdateStatus() {
        when(convRepo.findByTripId(10L)).thenReturn(Optional.of(conversation));

        service.updateStatus(10L, Status.ACTIVE);

        assertTrue(conversation.isActive());
        assertEquals(Status.ACTIVE, conversation.getTravelStatus());
        verify(eventPublisher, never()).publish(any(), any(), any());
    }

    @Test
    void testShouldUpdateStatusInCourse() {
        when(convRepo.findByTripId(10L)).thenReturn(Optional.of(conversation));

        service.updateStatus(10L, Status.IN_COURSE);

        assertTrue(conversation.isActive());
        assertEquals(Status.IN_COURSE, conversation.getTravelStatus());
        verify(eventPublisher, never()).publish(any(), any(), any());
    }

    @Test
    void testShouldUpdateStatusAndPublishesEvent() {
        when(convRepo.findByTripId(10L)).thenReturn(Optional.of(conversation));

        service.updateStatus(10L, Status.COMPLETED);

        assertFalse(conversation.isActive());
        assertEquals(Status.COMPLETED, conversation.getTravelStatus());

        verify(eventPublisher, times(1)).publish(
                any(TravelCompletedEvent.class),
                eq(RabbitMQConfig.TRIP_EXCHANGE),
                eq(RabbitMQConfig.TRIP_FINISHED_ROUTING_KEY)
        );
    }

    @Test
    void testShouldUpdateStatusWithoutEvent() {
        when(convRepo.findByTripId(10L)).thenReturn(Optional.of(conversation));

        service.updateStatus(10L, Status.CANCELLED);

        assertFalse(conversation.isActive());
        assertEquals(Status.CANCELLED, conversation.getTravelStatus());
        verify(eventPublisher, never()).publish(any(), any(), any());
    }

    @Test
    void testShouldUpdateStatusNoConversation_throwsException() {
        when(convRepo.findByTripId(999L)).thenReturn(Optional.empty());

        ConversationException ex = assertThrows(ConversationException.class, () -> {
            service.updateStatus(999L, Status.ACTIVE);
        });

        assertTrue(ex.getMessage().contains("No existe conversación para el tripId:"));
    }

    @Test
    void testShouldToMessageResponse() {
        Message msg = new Message("abc123", "1", "Hola");
        MessageResponse mockResponse = new MessageResponse();

        when(mapper.toMessageResponse(msg)).thenReturn(mockResponse);

        MessageResponse result = service.toMessageResponse(msg);

        assertSame(mockResponse, result);
        verify(mapper, times(1)).toMessageResponse(msg);
    }
}

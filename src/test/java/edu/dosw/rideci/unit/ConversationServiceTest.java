package edu.dosw.rideci.unit;

import static org.mockito.ArgumentMatchers.*;
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
        conversation.setTravelId("ww");
        conversation.setParticipants(List.of(1L, 2L));
        conversation.setType(TravelType.TRIP);
        conversation.setActive(true);
        conversation.setTravelStatus(Status.ACTIVE);
    }

    @Test
    void testShouldCreateChatWhenActive() {
        CreateConversationCommand cmd = mock(CreateConversationCommand.class);
        when(cmd.getTravelId()).thenReturn("ww");
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
        when(cmd.getTravelId()).thenReturn("ww");
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
        when(cmd.getTravelId()).thenReturn("w");
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
        when(cmd.getTravelId()).thenReturn("w");
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
        when(convRepo.findById("abc123")).thenReturn(Optional.of(conversation));
        Message message = new Message("abc123", "2", "1", "Hola");

        service.sendMessage("abc123", message);
        verify(msgRepo, times(1)).save(message);
    }

    @Test
    void testShouldNotSendMessageConversationNotFound() {
        when(convRepo.findById("404")).thenReturn(Optional.empty());

        Message message = new Message("404", "1","2", "Hola");
        assertThrows(ConversationException.class, () -> service.sendMessage("404", message));
    }


 

    @Test
    void testShouldGetMessages() {
        when(convRepo.existsById("abc123")).thenReturn(true);

        when(msgRepo.findByConversationId("abc123"))
                .thenReturn(List.of(
                        new Message("abc123", "1", "2", "Hola"),
                        new Message("abc123", "2", "3", "Chao")
                ));

        when(mapper.toMessageResponse(any())).thenReturn(new MessageResponse());

        List<MessageResponse> result = service.getMessages("abc123");
        assertEquals(2, result.size());
    }

    @Test
    void testShouldGetMessagesNotFound_throwsException() {
        when(convRepo.existsById("missing")).thenReturn(false);
        assertThrows(ConversationException.class, () -> service.getMessages("missing"));
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
        when(convRepo.findByTravelId("w")).thenReturn(Optional.of(conversation));

        service.updateStatus("w", Status.ACTIVE);

        assertTrue(conversation.isActive());
        assertEquals(Status.ACTIVE, conversation.getTravelStatus());
        verify(eventPublisher, never()).publish(any(), any(), any());
    }

    @Test
    void testShouldUpdateStatusInCourse() {
        when(convRepo.findByTravelId("w")).thenReturn(Optional.of(conversation));

        service.updateStatus("w", Status.IN_COURSE);

        assertTrue(conversation.isActive());
        assertEquals(Status.IN_COURSE, conversation.getTravelStatus());
        verify(eventPublisher, never()).publish(any(), any(), any());
    }

    @Test
    void testShouldUpdateStatusAndPublishesEvent() {
        when(convRepo.findByTravelId("w")).thenReturn(Optional.of(conversation));

        service.updateStatus("w", Status.COMPLETED);

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
        when(convRepo.findByTravelId("w")).thenReturn(Optional.of(conversation));

        service.updateStatus("w", Status.CANCELLED);

        assertFalse(conversation.isActive());
        assertEquals(Status.CANCELLED, conversation.getTravelStatus());
        verify(eventPublisher, never()).publish(any(), any(), any());
    }

    @Test
    void testShouldUpdateStatusNoConversation_throwsException() {
        when(convRepo.findByTravelId("oko")).thenReturn(Optional.empty());
        assertThrows(ConversationException.class, () -> service.updateStatus("oko", Status.ACTIVE));
    }

    @Test
    void testShouldGetAllConversations() {
        Conversation c1 = new Conversation();
        Conversation c2 = new Conversation();

        when(convRepo.findAll()).thenReturn(List.of(c1, c2));
        when(mapper.toConversationResponse(any()))
                .thenReturn(new ConversationResponse());

        List<ConversationResponse> result = service.getAllConversations();

        assertEquals(2, result.size());
        verify(convRepo, times(1)).findAll();
        verify(mapper, times(2)).toConversationResponse(any());
    }

    @Test
    void testShouldGetAllConversationsEmpty() {
        when(convRepo.findAll()).thenReturn(List.of());

        List<ConversationResponse> result = service.getAllConversations();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    void shouldAddOrganizerWhenGroupChatAndOrganizerPresent() {
        CreateConversationCommand cmd = mock(CreateConversationCommand.class);

        when(cmd.getChatType()).thenReturn(TravelType.GROUP);
        when(cmd.getParticipants()).thenReturn(List.of(1L, 2L));
        when(cmd.getOrganizerId()).thenReturn(99L);
        when(cmd.getTravelStatus()).thenReturn(Status.ACTIVE);
        when(cmd.getTravelId()).thenReturn("t1");

        ArgumentCaptor<Conversation> captor =
                ArgumentCaptor.forClass(Conversation.class);

        when(convRepo.save(any())).thenAnswer(inv -> {
            Conversation c = inv.getArgument(0);
            c.setId("1");
            return c;
        });

        service.createChat(cmd);

        verify(convRepo).save(captor.capture());

        assertTrue(captor.getValue().getParticipants().contains(99L));
    }

    @Test
    void shouldNotAddOrganizerWhenGroupChatAndOrganizerIsNull() {
        CreateConversationCommand cmd = mock(CreateConversationCommand.class);

        when(cmd.getChatType()).thenReturn(TravelType.GROUP);
        when(cmd.getParticipants()).thenReturn(List.of(1L, 2L));
        when(cmd.getOrganizerId()).thenReturn(null);
        when(cmd.getTravelStatus()).thenReturn(Status.ACTIVE);
        when(cmd.getTravelId()).thenReturn("t2");

        ArgumentCaptor<Conversation> captor =
                ArgumentCaptor.forClass(Conversation.class);

        when(convRepo.save(any())).thenReturn(new Conversation());

        service.createChat(cmd);

        verify(convRepo).save(captor.capture());

        assertEquals(List.of(1L, 2L), captor.getValue().getParticipants());
    }

    @Test
    void shouldAddDriverWhenTripChatAndDriverPresent() {
        CreateConversationCommand cmd = mock(CreateConversationCommand.class);

        when(cmd.getChatType()).thenReturn(TravelType.TRIP);
        when(cmd.getParticipants()).thenReturn(List.of(3L));
        when(cmd.getDriverId()).thenReturn(77L);
        when(cmd.getTravelStatus()).thenReturn(Status.ACTIVE);
        when(cmd.getTravelId()).thenReturn("t3");

        ArgumentCaptor<Conversation> captor =
                ArgumentCaptor.forClass(Conversation.class);

        when(convRepo.save(any())).thenReturn(new Conversation());

        service.createChat(cmd);

        verify(convRepo).save(captor.capture());

        assertTrue(captor.getValue().getParticipants().contains(77L));
    }

    @Test
    void shouldNotAddDriverWhenTripChatAndDriverIsNull() {
        CreateConversationCommand cmd = mock(CreateConversationCommand.class);

        when(cmd.getChatType()).thenReturn(TravelType.TRIP);
        when(cmd.getParticipants()).thenReturn(List.of(3L));
        when(cmd.getDriverId()).thenReturn(null);
        when(cmd.getTravelStatus()).thenReturn(Status.ACTIVE);
        when(cmd.getTravelId()).thenReturn("t4");

        ArgumentCaptor<Conversation> captor =
                ArgumentCaptor.forClass(Conversation.class);

        when(convRepo.save(any())).thenReturn(new Conversation());

        service.createChat(cmd);

        verify(convRepo).save(captor.capture());

        assertEquals(List.of(3L), captor.getValue().getParticipants());
    }

    @Test
    void testShouldToMessageResponse() {
        Message msg = new Message("abc123", "2", "1", "Hola");
        MessageResponse mockResponse = new MessageResponse();

        when(mapper.toMessageResponse(msg)).thenReturn(mockResponse);

        MessageResponse result = service.toMessageResponse(msg);
        assertSame(mockResponse, result);
        verify(mapper, times(1)).toMessageResponse(msg);
    }
}

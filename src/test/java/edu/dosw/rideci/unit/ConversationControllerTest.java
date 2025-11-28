package edu.dosw.rideci.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.rideci.application.dtos.request.CreateConversationRequest;
import edu.dosw.rideci.application.dtos.request.SendMessageRequest;
import edu.dosw.rideci.application.dtos.response.ConversationResponse;
import edu.dosw.rideci.application.dtos.response.MessageResponse;
import edu.dosw.rideci.application.service.ConversationService;
import edu.dosw.rideci.domain.entities.Message;
import edu.dosw.rideci.domain.enums.TravelType;
import edu.dosw.rideci.infrastructure.api.controllers.ConversationController;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ConversationController.class)
class ConversationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConversationService service;

    @Autowired
    private ObjectMapper mapper;


    @Test
    void testShouldCreateConversation() throws Exception {

        CreateConversationRequest req = new CreateConversationRequest();
        req.setTripId(50L);
        req.setType(TravelType.TRIP);
        req.setParticipants(List.of(1L, 2L));

        ConversationResponse fakeResp = new ConversationResponse();
        fakeResp.setId("conv123");
        fakeResp.setTripId(50L);

        Mockito.when(service.createChat(any())).thenReturn("conv123");
        Mockito.when(service.getConversation("conv123")).thenReturn(fakeResp);

        String json = mapper.writeValueAsString(req);

        @SuppressWarnings("null")
        String response = mockMvc.perform(
                        post("/conversations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("conv123"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ConversationResponse out = mapper.readValue(response, ConversationResponse.class);
        assertEquals("conv123", out.getId());
    }


    @Test
    void testShouldGetConversation() throws Exception {

        ConversationResponse resp = new ConversationResponse();
        resp.setId("conv222");
        resp.setTripId(20L);

        Mockito.when(service.getConversation("conv222")).thenReturn(resp);

        String result = mockMvc.perform(get("/conversations/conv222"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("conv222"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ConversationResponse out = mapper.readValue(result, ConversationResponse.class);
        assertEquals("conv222", out.getId());
    }

    @Test
    void testShouldGetMessages() throws Exception {

        MessageResponse m1 = new MessageResponse();
        m1.setMessageId("m1");

        MessageResponse m2 = new MessageResponse();
        m2.setMessageId("m2");

        Mockito.when(service.getMessages("conv123"))
                .thenReturn(List.of(m1, m2));

        String result = mockMvc.perform(get("/conversations/conv123/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].messageId").value("m1"))
                .andExpect(jsonPath("$[1].messageId").value("m2"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<?> list = mapper.readValue(result, List.class);
        assertEquals(2, list.size());
    }

    @Test
    void testShouldSendMessage() throws Exception {

        SendMessageRequest req = new SendMessageRequest();
        req.setSenderId("7");
        req.setContent("Hola mundo");

        MessageResponse fakeMessage = new MessageResponse();
        fakeMessage.setMessageId("msg999");
        fakeMessage.setConversationId("conv111");
        fakeMessage.setSenderId("7");
        fakeMessage.setContent("Hola mundo");
        fakeMessage.setTimestamp(new Date());

        Mockito.doNothing().when(service).sendMessage(anyString(), any(Message.class));
        Mockito.when(service.toMessageResponse(any(Message.class))).thenReturn(fakeMessage);

        String json = mapper.writeValueAsString(req);

        @SuppressWarnings("null")
        String result = mockMvc.perform(
                        post("/conversations/conv111/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageId").value("msg999"))
                .andExpect(jsonPath("$.senderId").value("7"))
                .andExpect(jsonPath("$.content").value("Hola mundo"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        MessageResponse out = mapper.readValue(result, MessageResponse.class);
        assertEquals("msg999", out.getMessageId());
        assertEquals("7", out.getSenderId());
    }
}

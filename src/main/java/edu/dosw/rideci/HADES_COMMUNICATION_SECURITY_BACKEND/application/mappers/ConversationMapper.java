package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.Conversation;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.Message;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response.ConversationResponse;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response.MessageResponse;

@Mapper(componentModel = "spring")
public interface ConversationMapper {

    @Mapping(source = "id", target = "id")
    ConversationResponse toConversationResponse(Conversation conversation);

    MessageResponse toMessageResponse(Message message);
}
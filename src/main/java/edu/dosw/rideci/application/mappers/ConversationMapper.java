package edu.dosw.rideci.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import edu.dosw.rideci.domain.entities.Conversation;
import edu.dosw.rideci.domain.entities.Message;
import edu.dosw.rideci.application.dtos.response.ConversationResponse;
import edu.dosw.rideci.application.dtos.response.MessageResponse;

@Mapper(componentModel = "spring")
public interface ConversationMapper {

    @Mapping(source = "id", target = "id")
    ConversationResponse toConversationResponse(Conversation conversation);

    MessageResponse toMessageResponse(Message message);
}
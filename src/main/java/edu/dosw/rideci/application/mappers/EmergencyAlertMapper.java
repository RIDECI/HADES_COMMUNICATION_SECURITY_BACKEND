package edu.dosw.rideci.application.mappers;

import edu.dosw.rideci.application.dtos.request.EmergencyAlertRequest;
import edu.dosw.rideci.application.dtos.response.EmergencyAlertResponse;
import edu.dosw.rideci.domain.entities.EmergencyAlert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmergencyAlertMapper {

    EmergencyAlertMapper INSTANCE = Mappers.getMapper(EmergencyAlertMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "additionalInfo", ignore = true)
    EmergencyAlert toEntity(EmergencyAlertRequest dto);

    EmergencyAlertResponse toDTO(EmergencyAlert alert);
}
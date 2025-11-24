package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.mappers;

import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.request.EmergencyAlertRequest;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.application.dtos.response.EmergencyAlertResponse;
import edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND.domain.entities.EmergencyAlert;
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

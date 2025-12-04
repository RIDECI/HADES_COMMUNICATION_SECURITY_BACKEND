package edu.dosw.rideci.application.mappers;

import edu.dosw.rideci.application.dtos.request.AutomaticReportRequest;
import edu.dosw.rideci.application.dtos.request.EmergencyReportRequest;
import edu.dosw.rideci.application.dtos.request.ManualReportRequest;
import edu.dosw.rideci.application.dtos.response.ReportResponse;
import edu.dosw.rideci.domain.entities.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", constant = "MANUAL")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Report toManualEntity(ManualReportRequest dto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", constant = "DETOUR")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Report toAutomaticEntity(AutomaticReportRequest dto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", constant = "EMERGENCY")
    @Mapping(target = "description", constant = "Emergency button activated")
    @Mapping(target = "targetId", ignore = true) // No aplica en emergencias
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Report toEmergencyEntity(EmergencyReportRequest dto);

    ReportResponse toDTO(Report entity);
}




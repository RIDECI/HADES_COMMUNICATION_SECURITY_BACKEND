package edu.dosw.rideci.application.mappers;

import edu.dosw.rideci.application.dtos.request.ReportRequest;
import edu.dosw.rideci.application.dtos.response.ReportResponse;
import edu.dosw.rideci.domain.entities.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Report toEntity(ReportRequest dto);

    ReportResponse toDTO(Report entity);
}



package com.example.studentmanagement.mapper;

import com.example.studentmanagement.dto.SubjectDto;
import com.example.studentmanagement.entity.Subject;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    SubjectDto entityToDto(Subject subject);
    List<SubjectDto> entitiesToDtos(List<Subject> subjectList);
    Subject dtoToEntity(SubjectDto subjectDto);
}

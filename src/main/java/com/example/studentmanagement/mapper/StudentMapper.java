package com.example.studentmanagement.mapper;

import com.example.studentmanagement.dto.StudentDto;
import com.example.studentmanagement.entity.Student;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto entityToDto(Student student);
    List<StudentDto> entitiesToDtos(List<Student> studentList);
    Student dtoToEntity(StudentDto studentDto);
}

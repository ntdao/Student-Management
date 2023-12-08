package com.example.studentmanagement.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDtoTest {
    private UserDto user;
    private SubjectDto subject;
    private Float mark;
}

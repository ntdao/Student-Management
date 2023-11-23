package com.example.studentmanagement.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto implements Serializable {
    private String subjectCode;
    private String name;
}

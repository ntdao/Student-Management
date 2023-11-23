package com.example.studentmanagement.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto implements Serializable {
    private String studentCode;
    private String subjectCode;
    private Float mark;
}

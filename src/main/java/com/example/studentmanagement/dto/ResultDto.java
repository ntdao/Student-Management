package com.example.studentmanagement.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto implements Serializable {
    private String userCode;
    private String subjectCode;
    private Float mark;
}

package com.example.studentmanagement.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto implements Serializable {
    @NotBlank(message = "Subject code is mandatory")
    private String subjectCode;
    @NotBlank(message = "Subject name is mandatory")
    private String name;
}

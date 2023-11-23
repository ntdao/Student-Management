package com.example.studentmanagement.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto implements Serializable {
        private String studentCode;
        private String name;
        private String email;
        private String phone;
        private LocalDate dob;
        private String address;
        private String imgUrl;
}

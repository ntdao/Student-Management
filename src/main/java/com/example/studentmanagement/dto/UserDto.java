package com.example.studentmanagement.dto;

import com.example.studentmanagement.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    @NotBlank(message = "User code is mandatory")
    private String userCode;
    private String name;
    @NotBlank(message = "Email is mandatory")
    private String email;
    private String phone;
    private LocalDate dob;
    private String address;
    private String imgUrl;
    private Role role;
    private List<ResultDtoTest> results;
}

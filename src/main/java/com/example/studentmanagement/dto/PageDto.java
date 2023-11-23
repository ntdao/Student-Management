package com.example.studentmanagement.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private String sortDir;
    private String key;
}

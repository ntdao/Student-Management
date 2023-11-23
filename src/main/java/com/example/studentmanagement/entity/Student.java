package com.example.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    private Integer gender;
    @Column(nullable = false)
    private String email;
    private String phone;
    private LocalDate dob;
    private String address;
    private String imgUrl;
    @Column(nullable = false, updatable = false)
    private String studentCode;
    private boolean deleteFlag = false;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
//    @Fetch(FetchMode.JOIN) -> truy vấn n+1 ở bảng result
//    @Fetch(FetchMode.SELECT) -> lỗi lazy...
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Result> resultList = new ArrayList<>();
}


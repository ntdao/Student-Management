package com.example.studentmanagement.entity;

import com.example.studentmanagement.dto.ResultDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@NamedNativeQuery(name = "Result.findStudentResult",
        query = "select u.user_code, s.subject_code, r.mark " +
                "from result r " +
                "join user u on u.id = r.user_id " +
                "join subject s on s.id = r.subject_id " +
                "where r.user_id = :id",
        resultSetMapping = "Mapping.ResultDto")
@SqlResultSetMapping(name = "Mapping.ResultDto",
        classes = @ConstructorResult(targetClass = ResultDto.class,
                columns = {@ColumnResult(name = "user_code"),
                        @ColumnResult(name = "subject_code"),
                        @ColumnResult(name = "mark")}))

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float mark;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
//    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @ToString.Exclude
    private Subject subject;
}

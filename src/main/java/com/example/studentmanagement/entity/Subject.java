package com.example.studentmanagement.entity;

import com.example.studentmanagement.dto.ResultDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@NamedNativeQuery(name = "Subject.findById",
        query = "select s.name, s.subject_code from subject s where s.id = :id",
        resultSetMapping = "Mapping.Subject")
@SqlResultSetMapping(name = "Mapping.Subject",
        classes = @ConstructorResult(targetClass = Subject.class,
                columns = {@ColumnResult(name = "name"),
                        @ColumnResult(name = "subject_code")}))

public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String subjectCode;

    public Subject(String name) {
        this.name = name;
    }

    public Subject(String name, String subjectCode) {
        this.name = name;
        this.subjectCode = subjectCode;
    }
}

package com.example.studentmanagement.repository;

import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.entity.Result;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    @Query("select new com.example.studentmanagement.dto.ResultDto(std.studentCode, sub.subjectCode, r.mark) " +
            "from Result r " +
            "join Student std on std.id = r.student.id " +
            "join Subject sub on sub.id = r.subject.id " +
            "where r.student.id = :id")
    List<ResultDto> findResultByStudent(Long id);

    @Query("""
            select r from Result r
            left join fetch r.student
            left join fetch r.subject
            """)
    List<Result> findAllResults();

    @Query("select new com.example.studentmanagement.dto.ResultDto(std.studentCode, sub.subjectCode, r.mark) " +
            "from Result r " +
            "join Student std on std.id = r.student.id " +
            "join Subject sub on sub.id = r.subject.id")
    List<ResultDto> findAllResultsDto();

    @Query("select new com.example.studentmanagement.dto.ResultDto(std.studentCode, sub.subjectCode, r.mark) " +
            "from Result r " +
            "join Student std on std.id = r.student.id " +
            "join Subject sub on sub.id = r.subject.id " +
            "where r.subject.id = :id " +
            "and std.deleteFlag = false")
    List<ResultDto> findResultBySubject(Long id);

    boolean existsByStudentAndSubject(Student student, Subject subject);

    Optional<Result> findByStudentAndSubject(Student student, Subject subject);
    @Query(value = "select * from result r " +
            "join student std on std.id = r.student_id " +
            "join subject sub on sub.id = r.subject_id " +
            "where r.student_id = :id", nativeQuery = true)
    List<Map<String, String>> findAllObject();
}

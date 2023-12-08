package com.example.studentmanagement.repository;

import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.entity.Result;
import com.example.studentmanagement.entity.Subject;
import com.example.studentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
//        @Query("select new com.example.studentmanagement.dto.ResultDto(u.userCode, s.subjectCode, r.mark_name) " +
//            "from Result r " +
//            "join User u on u.id = r.user.id " +
//            "join Subject s on s.id = r.subject.id " +
//            "where r.user.id = :id")
    @Query(name = "Result.findStudentResult", nativeQuery = true)
    List<ResultDto> findStudentResult(Long id);

    @Query(value = "select * from result r " +
            "join user u on r.user_id = u.id " +
            "join subject s on r.subject_id = s.id " +
            "where u.id = :id", nativeQuery = true)
    List<Result> findStudentResultById(Long id);

    @Query("select r from Result r " +
            "join fetch r.user " +
            "join fetch r.subject " +
            "where r.user.id = :id")
    List<Result> findStudentResultByStudentId(Long id);

    @Query(value = "select u.user_code as userCode, s.subject_code as subjectCode, r.mark as mark " +
            "from result r " +
            "join user u on u.id = r.user_id " +
            "join subject s on s.id = r.subject_id " +
            "where r.user_id = :id",
            nativeQuery = true)
    List<Map<String, Object>> findResultByStudent(Long id);

    @Query(value = "select u.user_code as userCode, s.subject_code as subjectCode, r.mark as mark " +
            "from result r " +
            "join user u on u.id = r.user_id " +
            "join subject s on s.id = r.subject_id " +
            "where r.user_id = :id",
            nativeQuery = true)
    List<Tuple> findResultByStudentId(Long id);

    @Query("select r from Result r " +
            "join fetch r.user " +
            "join fetch r.subject")
    List<Result> findAllResults();

    @Query("select new com.example.studentmanagement.dto.ResultDto(u.userCode, s.subjectCode, r.mark) " +
            "from Result r " +
            "join User u on u.id = r.user.id " +
            "join Subject s on s.id = r.subject.id")
    List<ResultDto> findAllResultsDto();

    @Query("select new com.example.studentmanagement.dto.ResultDto(u.userCode, s.subjectCode, r.mark) " +
            "from Result r " +
            "join User u on u.id = r.user.id " +
            "join Subject s on s.id = r.subject.id " +
            "where r.subject.id = :id " +
            "and u.deleteFlag = false")
    List<ResultDto> findResultBySubject(Long id);

    @Query(value = "select * from result r " +
            "join user u on r.user_id = u.id " +
            "join subject s on r.subject_id = s.id " +
            "where s.id = :id", nativeQuery = true)
    List<Result> findSubjectResult(Long id);

    @Query("select r from Result r " +
            "join fetch r.user " +
            "join fetch r.subject " +
            "where r.subject.id = :id")
    List<Result> findSubjectResultById(Long id);

    boolean existsByUserAndSubject(User user, Subject subject);

    Optional<Result> findByUserAndSubject(User user, Subject subject);

    @Modifying
    @Query("delete from Result r " +
            "where r.subject.id = :id")
    void deleteBySubject(Long id);
}

package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    boolean existsByName(String name);

    boolean existsBySubjectCode(String subjectCode);

    Optional<Subject> findBySubjectCode(String subjectCode);

    List<Subject> findBySubjectCodeContainsIgnoreCase(String subjectCode);

    List<Subject> findByNameContainsIgnoreCase(String name);

    @Query("select s from Subject s " +
            "where s.name like %:key% " +
            "or s.subjectCode like %:key%")
    Page<Subject> findByKeyContainsIgnoreCase(String key, Pageable paging);

    @Query("select new Subject (s.name, s.subjectCode) from Subject s " +
            "where s.id = :id")
    Optional<Subject> findSubjectBySubjectId(Long id);

    @Query(name="Subject.findById", nativeQuery = true)
    Optional<Subject> findBySubjectId(Long id);

    @Query(value = "select s.name, s.subjectCode from Subject s " +
            "where s.id = :id")
    List<Object[]> findObjectBySubjectId(Long id);

    @Query(value = "select s.name from subject s " +
            "where s.id = :id", nativeQuery = true)
    Optional<Tuple> findTupleBySubjectId(Long id);

    @Modifying
    @Query("delete from Subject s " +
            "where s.id = :id")
    void deleteSubjectById(Long id);
}

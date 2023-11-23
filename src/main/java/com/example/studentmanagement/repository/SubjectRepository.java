package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    boolean existsByName(String name);

    boolean existsBySubjectCode(String subjectCode);

    Optional<Subject> findBySubjectCode(String subjectCode);

    List<Subject> findBySubjectCodeContainsIgnoreCase(String subjectCode);

    List<Subject> findByNameContainsIgnoreCase(String name);

    Page<Subject> findByNameContainsIgnoreCase(String key, Pageable paging);
}

package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);

    boolean existsByStudentCode(String studentCode);

    boolean existsByIdAndDeleteFlagFalse(Long id);

    Optional<Student> findByStudentCodeAndDeleteFlagFalse(String studentCode);

    List<Student> findByStudentCodeContainsIgnoreCaseAndDeleteFlagFalse(String studentCode);

    List<Student> findByNameContainsIgnoreCaseAndDeleteFlagFalse(String name);

    List<Student> findByDeleteFlagFalse();

    Page<Student> findByDeleteFlagFalse(Pageable paging);

    Optional<Student> findByIdAndDeleteFlagFalse(Long id);

    Page<Student> findByNameContainsIgnoreCaseAndDeleteFlagFalse(String name, Pageable paging);
}

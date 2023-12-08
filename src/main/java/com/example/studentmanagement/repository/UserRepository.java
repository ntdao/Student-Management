package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByUserCode(String studentCode);

    boolean existsByIdAndDeleteFlagFalse(Long id);

    Optional<User> findByUserCodeAndDeleteFlagFalse(String studentCode);

    List<User> findByUserCodeContainsIgnoreCaseAndDeleteFlagFalse(String studentCode);

    List<User> findByNameContainsIgnoreCaseAndDeleteFlagFalse(String name);

    List<User> findByDeleteFlagFalse();

    Optional<User> findByIdAndDeleteFlagFalse(Long id);

    Optional<User> findByEmail(String email);

    @Query("select u from User u " +
            "where u.name like %?1% " +
            "and u.deleteFlag = false " +
            "and u.role = 'STUDENT'")
    Page<User> findStudentByName(String key, Pageable paging);

//    @Query(value = "select u from User u " +
//            "join fetch u.results " +
//            "where u.deleteFlag = false and u.id = :id")
//    Optional<User> findByUserId(Long id);
}

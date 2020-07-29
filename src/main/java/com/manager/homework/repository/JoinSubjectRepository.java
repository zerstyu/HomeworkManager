package com.manager.homework.repository;

import com.manager.homework.domain.JoinSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoinSubjectRepository extends JpaRepository<JoinSubject, Long> {
    Optional<JoinSubject> findByUserIdAndSubjectId(Long userId, Long subjectId);

    void deleteByUserIdAndSubjectId(Long userId, Long subjectId);
}

package com.manager.homework.repository;

import com.manager.homework.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<String> findByEmail(String email);
    Subject findByEmailAndName(String email, String name);
    boolean existsSubjectByEmailAndName(String email, String name);
}

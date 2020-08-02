package com.manager.homework.repository;

import com.manager.homework.domain.JoinSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinSubjectRepository extends JpaRepository<JoinSubject, Long> {
}

package com.manager.homework.repository;

import com.manager.homework.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepositoryCustom {
    List<Subject> selectAll(String memberId);
}

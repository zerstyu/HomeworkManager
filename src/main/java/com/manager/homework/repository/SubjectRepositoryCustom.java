package com.manager.homework.repository;

import com.manager.homework.domain.Subject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepositoryCustom {
    List<Subject> selectAll(Long userId);
}
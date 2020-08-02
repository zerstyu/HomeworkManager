package com.manager.homework.repository;

import com.manager.homework.vo.SubjectResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepositoryCustom {
    List<SubjectResponse> selectAllSubjectList(Long userId);
}


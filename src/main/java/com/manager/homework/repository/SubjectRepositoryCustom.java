package com.manager.homework.repository;

import com.manager.homework.vo.SearchSubjectDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepositoryCustom {
    List<SearchSubjectDto> selectAllSubjectList(Long userId);
}


package com.manager.homework.repository;

import com.manager.homework.vo.SearchSubjectDto;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepositoryCustom {
    SearchSubjectDto selectAllSubjectList(Long userId);
//    SearchSubjectDto findByCondition(RequestSubjectDto dto);
}


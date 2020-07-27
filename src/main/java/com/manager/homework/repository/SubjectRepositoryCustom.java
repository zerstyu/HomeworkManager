package com.manager.homework.repository;

import com.manager.homework.vo.SubjectDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepositoryCustom {
    List<SubjectDto> selectSubjectList(Long userId);
}

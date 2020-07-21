package com.manager.homework.repository;

import com.manager.homework.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>, SubjectRepositoryCustom {
    List<Subject> findByMemberId(String memberId);

    Subject findByMemberIdAndName(String memberId, String name);

    boolean existsByMemberIdAndName(String memberId, String name);
}

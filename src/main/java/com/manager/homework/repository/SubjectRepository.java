package com.manager.homework.repository;

import com.manager.homework.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>, SubjectRepositoryCustom {
    boolean existsByInviteCode(String inviteCode);
}

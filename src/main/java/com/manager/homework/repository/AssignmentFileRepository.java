package com.manager.homework.repository;

import com.manager.homework.domain.AssignmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentFileRepository extends JpaRepository<AssignmentFile, Long> {
    List<AssignmentFile> findByAssignmentId(Long assignmentId);
}

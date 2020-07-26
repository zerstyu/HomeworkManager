package com.manager.homework.repository;

import com.manager.homework.domain.AssignmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentFileRepository extends JpaRepository<AssignmentFile, Long> {
}

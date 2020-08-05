package com.manager.homework.repository;

import com.manager.homework.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByIsOpen(Boolean isOpen);
}

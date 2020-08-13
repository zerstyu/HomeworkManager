package com.manager.homework.repository;

import com.manager.homework.domain.WrongAnswerNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WrongAnswerNoteRepository extends JpaRepository<WrongAnswerNote, Long> {
    List<WrongAnswerNote> findByUserId(Long id);
}

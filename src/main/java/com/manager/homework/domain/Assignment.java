package com.manager.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignments")
@Data
// 과제 공지
public class Assignment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "NOTICE_ID")
    private Notice notice;

    @Column
    private String feedback;

    @Column
    private int score;

    @Column
    private Boolean isOpen;

    @Column
    private String note;
}

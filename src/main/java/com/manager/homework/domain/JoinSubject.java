package com.manager.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "join_subjects")
// 참여한 과목
public class JoinSubject extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "MAKE_USER_ID")
    private User makeUser;
}

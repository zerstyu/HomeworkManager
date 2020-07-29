package com.manager.homework.domain;

import com.manager.homework.type.NoticeStatus;
import com.manager.homework.type.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notices")
@Data
// 과제 공지
public class Notice extends BaseEntity {
    @Column
    private NoticeType type;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @Column
    private NoticeStatus status;

    @Column
    private String title;

    @Column
    private String content;

    @Column(name = "EXPIRED_AT")
    private LocalDate expiredAt;
}
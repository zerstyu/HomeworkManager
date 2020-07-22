package com.manager.homework.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "homework")
@Data
// 제출한 파일
public class Homework extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @Column
    private String title;

    @Column
    private String content;
}
package com.manager.homework.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "subjects")
// 과목
public class Subject extends BaseEntity {
    @Column(length = 20, nullable = false)
    private String name;
}

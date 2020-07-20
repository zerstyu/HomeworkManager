package com.manager.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "subjects")
// 과목
public class Subject extends BaseEntity {
    @Column(length = 20, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String name;
}

package com.manager.homework.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "belongs")
// 소속
public class Belong extends BaseEntity {
    @Column(length = 20, nullable = false)
    private String name;
}

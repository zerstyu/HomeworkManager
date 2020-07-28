package com.manager.homework.domain;

import com.manager.homework.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
// 사용자
public class User extends BaseEntity {
    @Column(length = 20, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20)
    private String groupName;

    @Column
    private LocalDate birthday;

    @Column
    private Gender gender;
}

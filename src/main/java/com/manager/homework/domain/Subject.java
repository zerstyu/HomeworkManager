package com.manager.homework.domain;

import com.manager.homework.vo.SubjectDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "subjects")
// 과목
public class Subject extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    private String inviteCode;

    public static SubjectDto toDto(Subject entity) {
        return new ModelMapper().map(entity, SubjectDto.class);
    }

}

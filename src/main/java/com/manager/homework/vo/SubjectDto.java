package com.manager.homework.vo;

import com.manager.homework.domain.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class SubjectDto {
    private String email;
    private String name;
    private String changeName;

    public Subject toEntity() {
        return Subject.builder()
                .email(this.email)
                .name(this.name)
                .build();
    }
}
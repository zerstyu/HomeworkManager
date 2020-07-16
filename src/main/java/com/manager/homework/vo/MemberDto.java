package com.manager.homework.vo;

import com.manager.homework.domain.Member;
import lombok.Data;

@Data
public class MemberDto {
    private String email;
    private String password;

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}

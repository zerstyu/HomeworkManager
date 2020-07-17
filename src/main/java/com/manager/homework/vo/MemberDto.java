package com.manager.homework.vo;

import com.manager.homework.domain.Member;
import lombok.Data;

@Data
public class MemberDto {
    private String email;
    private String password;
    private String groupName;

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .groupName(groupName)
                .build();
    }
}

package com.manager.homework.vo;

import com.manager.homework.domain.User;
import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;
    private String name;
    private String groupName;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .groupName(groupName)
                .build();
    }
}
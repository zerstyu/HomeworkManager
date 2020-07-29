package com.manager.homework.vo;

import com.manager.homework.domain.User;
import com.manager.homework.type.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private String email;
    private String password;
    private String name;
    private String groupName;
    private LocalDate birthday;
    private Gender gender;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .groupName(groupName)
                .birthday(birthday)
                .gender(gender)
                .build();
    }
}

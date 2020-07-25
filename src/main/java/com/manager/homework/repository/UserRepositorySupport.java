package com.manager.homework.repository;

import com.manager.homework.domain.User;
import com.manager.homework.vo.UserDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.manager.homework.domain.QUser.user;

@Repository
public class UserRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public UserRepositorySupport(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }

    public List<User> findByCondition(UserDto userDto) {
        BooleanBuilder builder = new BooleanBuilder();
        if (userDto.getEmail() != null) {
            builder.and(user.email.contains(userDto.getEmail()));
        }

        if (userDto.getName() != null) {
            builder.and(user.name.contains(userDto.getName()));
        }

        if (userDto.getGroupName() != null) {
            builder.and(user.groupName.contains(userDto.getGroupName()));
        }

        return queryFactory.selectFrom(user)
                .where(builder)
                .fetch();
    }
}

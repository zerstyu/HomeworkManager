package com.manager.homework.repository;

import com.manager.homework.domain.Order;
import com.manager.homework.type.Gender;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.manager.homework.domain.QOrder.order;

@Repository
public class OrderRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public OrderRepositorySupport(JPAQueryFactory queryFactory) {
        super(Order.class);
        this.queryFactory = queryFactory;
    }

    public List<Order> findByCondition(Gender gender) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(order.user.gender.eq(gender));

        return queryFactory.selectFrom(order)
                .where(builder)
                .fetch();
    }
}
package com.manager.homework.repository;

import com.manager.homework.domain.Product;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.manager.homework.domain.QProduct.product;

@Repository
public class ProductRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public ProductRepositorySupport(JPAQueryFactory queryFactory) {
        super(Product.class);
        this.queryFactory = queryFactory;
    }

    public List<Product> findByCondition(String name) {
        BooleanBuilder builder = new BooleanBuilder();
        if (name != null) {
            builder.and(product.name.contains(name));
        }

        return queryFactory.selectFrom(product)
                .where(builder)
                .fetch();
    }
}
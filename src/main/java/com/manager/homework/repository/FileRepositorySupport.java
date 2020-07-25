package com.manager.homework.repository;

import com.manager.homework.domain.File;
import com.manager.homework.vo.FileDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.manager.homework.domain.QFile.file;

@Repository
public class FileRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public FileRepositorySupport(JPAQueryFactory queryFactory) {
        super(File.class);
        this.queryFactory = queryFactory;
    }

    public List<File> findByCondition(FileDto fileDto) {
        BooleanBuilder builder = new BooleanBuilder();
        if (fileDto.getType() != null) {
            builder.and(file.type.eq(fileDto.getType()));
        }

        if (fileDto.getUserId() != null) {
            builder.and(file.user.id.eq(fileDto.getUserId()));
        }

        if (fileDto.getSubjectId() != null) {
            builder.and(file.subject.id.eq(fileDto.getSubjectId()));
        }

        return queryFactory.selectFrom(file)
                .where(builder)
                .fetch();
    }
}

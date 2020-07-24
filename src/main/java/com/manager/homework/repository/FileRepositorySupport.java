package com.manager.homework.repository;

import com.manager.homework.domain.File;
import com.manager.homework.vo.FileDto;
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
        return queryFactory.selectFrom(file)
                .where(file.type.eq(fileDto.getType()),
                        file.user.id.eq(fileDto.getUserId()),
                        file.subject.id.eq(fileDto.getSubjectId()))
                .fetch();
    }
}

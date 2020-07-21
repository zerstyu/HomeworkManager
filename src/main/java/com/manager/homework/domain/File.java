package com.manager.homework.domain;

import com.manager.homework.type.FileType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Data
// 제출한 파일
public class File extends BaseEntity {
    @Column
    private FileType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @Column
    private String base64str;
}
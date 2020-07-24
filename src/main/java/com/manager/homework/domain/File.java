package com.manager.homework.domain;

import com.manager.homework.type.FileType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "files")
@Data
// 제출한 파일
public class File extends BaseEntity {
    @Column
    private FileType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @Lob
    @Column
    private String base64str;
}
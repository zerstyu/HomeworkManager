package com.manager.homework.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "modified_images")
@Data
// 수정 이미지
public class ModifiedImage extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @Column
    private String base64str;
}

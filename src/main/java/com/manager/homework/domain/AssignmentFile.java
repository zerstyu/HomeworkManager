package com.manager.homework.domain;

import com.manager.homework.type.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignment_files")
@Data
// 제출한 파일
public class AssignmentFile extends BaseEntity {
    @Column
    private FileType type;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ASSIGNMENT_ID")
    private Assignment assignment;

    @Lob
    @Column
    private String base64Str;
}
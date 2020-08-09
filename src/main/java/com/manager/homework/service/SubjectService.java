package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.manager.homework.domain.Subject;
import com.manager.homework.exception.CustomException;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.type.CategoryGroupType;
import com.manager.homework.type.ErrorCode;
import com.manager.homework.vo.CategoryGroupDto;
import com.manager.homework.vo.RequestSubjectDto;
import com.manager.homework.vo.SubjectDto;
import com.manager.homework.vo.SubjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final CommonService commonService;

    private static final int MAX_MAKE_RETRY = 5;

    public List<SubjectResponse> getSubjectList(Long userId) {
        return subjectRepository.selectAllSubjectList(userId);
    }

    public SubjectDto createSubject(RequestSubjectDto dto) {
        CategoryGroupType categoryGroupType = CategoryGroupType.findByCategoryCode(dto.getCategory());
        if (categoryGroupType != dto.getCategoryGroup()) {
            throw new CustomException(ErrorCode.SUBJECT_CATEGORY_INPUT_INVALID);
        }

        return Subject.toDto(
                subjectRepository.save(
                        Subject.builder()
                                .user(commonService.getUser(dto.getUserId()))
                                .name(dto.getSubjectName())
                                .inviteCode(makeRandomCode())
                                .categoryGroup(dto.getCategoryGroup())
                                .category(dto.getCategory())
                                .classType(dto.getClassType())
                                .build()));
    }

    public void updateSubject(Long subjectId, RequestSubjectDto dto) {
        Subject subject = commonService.getSubject(subjectId);
        subject.setName(dto.getSubjectName());
        subject.setCategoryGroup(dto.getCategoryGroup());
        subject.setCategory(dto.getCategory());
        subject.setClassType(dto.getClassType());
        subjectRepository.save(subject);
    }

    public void deleteSubject(Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }

    public SubjectDto getJoinSubject(String inviteCode) {
        return Subject.toDto(
                subjectRepository.findByInviteCode(inviteCode)
                        .orElseThrow(() -> new CustomException(ErrorCode.SUBJECT_INVITE_CODE_NONE)));
    }

    private String makeRandomCode() {
        String uuid;
        int retry = 0;

        do {
            uuid = UUID.randomUUID().toString().replace("-", "");
            retry++;

            if (retry > MAX_MAKE_RETRY) {
                throw new CustomException(ErrorCode.SUBJECT_INVITE_CODE_MAX_RETRY);
            }
        }
        while (subjectRepository.existsByInviteCode(uuid));

        return uuid;
    }

    public List<CategoryGroupDto> getCategoryList() {
        List<CategoryGroupType> categoryGroupTypeList = Arrays.asList(CategoryGroupType.values());
        List<CategoryGroupDto> categoryGroupDtoList = Lists.newArrayList();

        categoryGroupTypeList.forEach(
                it -> categoryGroupDtoList.add(
                        CategoryGroupDto.builder()
                                .categoryGroup(it)
                                .categoryGroupName(it.getCategoryGroup())
                                .categoryList(it.getCategoryCodeList().stream()
                                        .map(v -> CategoryGroupDto.CategoryDto.builder()
                                                .category(v)
                                                .categoryName(v.getCategory())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build()));

        return categoryGroupDtoList;
    }
}
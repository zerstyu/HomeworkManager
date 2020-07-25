package com.manager.homework.service;

import com.manager.homework.domain.Assignment;
import com.manager.homework.domain.Subject;
import com.manager.homework.domain.User;
import com.manager.homework.repository.AssignmentRepository;
import com.manager.homework.repository.AssignmentRepositorySupport;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.repository.UserRepository;
import com.manager.homework.vo.AssignmentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentRepositorySupport assignmentRepositorySupport;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public List<Assignment> getAssignmentList(AssignmentDto assignmentDto) {
        return assignmentRepositorySupport.findByCondition(assignmentDto);
    }

    public void createAssignment(AssignmentDto assignmentDto) throws Exception {
        assignmentRepository.save(convertToEntity(assignmentDto));
    }

    public Assignment getAssignment(Long id) {
        Optional<Assignment> assignmentEntityWrapper = assignmentRepository.findById(id);
        return assignmentEntityWrapper.get();
    }

    public Assignment updateAssignment(Long id, AssignmentDto assignmentDto) throws Exception {
        Optional<Assignment> assignmentEntityWrapper = assignmentRepository.findById(id);
        Assignment assignment = assignmentEntityWrapper.get();

        Optional<User> userEntityWrapper = userRepository.findById(assignmentDto.getUserId());
        assignment.setUser(userEntityWrapper.get());

        Optional<Subject> subjectEntityWrapper = subjectRepository.findById(assignmentDto.getSubjectId());
        assignment.setSubject(subjectEntityWrapper.get());

        assignment.setTitle(assignmentDto.getTitle());
        assignment.setContent(assignmentDto.getContent());

        assignmentRepository.save(assignment);
        return assignment;
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }

    private Assignment convertToEntity(AssignmentDto assignmentDto) {
        Optional<User> userEntityWrapper = userRepository.findById(assignmentDto.getUserId());
        Optional<Subject> subjectEntityWrapper = subjectRepository.findById(assignmentDto.getSubjectId());

        return Assignment.builder()
                .user(userEntityWrapper.get())
                .subject(subjectEntityWrapper.get())
                .title(assignmentDto.getTitle())
                .content(assignmentDto.getContent())
                .build();
    }
}

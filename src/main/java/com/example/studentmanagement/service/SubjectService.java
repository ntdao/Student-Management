package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.PageDto;
import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.dto.ResultDtoTest;
import com.example.studentmanagement.dto.SubjectDto;
import com.example.studentmanagement.entity.Result;
import com.example.studentmanagement.entity.Subject;
import com.example.studentmanagement.exception.ResourceAlreadyExistsException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.ResultRepository;
import com.example.studentmanagement.repository.SubjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public void addSubject(SubjectDto subjectDto) {
        isNameDuplicated(subjectDto.getName());
        isSubjectCodeDuplicated(subjectDto.getSubjectCode());
        subjectRepository.save(objectMapper.convertValue(subjectDto, Subject.class));
    }

    public void isNameDuplicated(String email) {
        if (subjectRepository.existsByName(email)) {
            throw new ResourceAlreadyExistsException("Subject name already taken");
        }
    }

    public void isSubjectCodeDuplicated(String email) {
        if (subjectRepository.existsBySubjectCode(email)) {
            throw new ResourceAlreadyExistsException("Subject code already taken");
        }
    }

    private void existById(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subject with id " + id + " was not found");
        }
    }

    @Transactional
    public void deleteSubject(Long id) {
        existById(id);
        resultRepository.deleteBySubject(id);
        subjectRepository.deleteSubjectById(id);
    }

    public List<SubjectDto> findAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(sub -> objectMapper.convertValue(sub, SubjectDto.class))
                .collect(Collectors.toList());
    }

    public List<SubjectDto> findAllSubjectsPageDto(PageDto pageDto) {
        Pageable paging = PageRequest.of(
                pageDto.getPageNo(),
                pageDto.getPageSize(),
                Sort.Direction.fromString(pageDto.getSortDir()),
                pageDto.getSortBy()
        );
        Page<Subject> pagedResult = subjectRepository.findByKeyContainsIgnoreCase(pageDto.getKey(), paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent().stream()
                    .map(sub -> objectMapper.convertValue(sub, SubjectDto.class))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

//    private Subject findById(Long id) {
//        return subjectRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Subject with id " + id + " was not found"));
//    }

    public Subject findBySubjectId(Long id) {
        existById(id);

        List<Object[]> sub = subjectRepository.findObjectBySubjectId(id);
        String name = String.valueOf(sub.get(0)[0]);
        System.out.println(name);
        String email = String.valueOf(sub.get(0)[1]);
        System.out.println(email);

        Tuple sub2 = subjectRepository.findTupleBySubjectId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject with id " + id + " was not found"));
        Subject subject2 = new Subject(sub2.get("name").toString());
        System.out.println(subject2);
        return subject2;
    }
    public Subject findById(Long id) {
        return subjectRepository.findSubjectBySubjectId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject with id " + id + " was not found"));
    }

    public Subject findSubjectId(Long id) {
        return subjectRepository.findBySubjectId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject with id " + id + " was not found"));
    }

    public SubjectDto findSubjectById(Long id) {
        Subject subject = findById(id);
        return objectMapper.convertValue(subject, SubjectDto.class);
    }

    private List<Subject> findByName(String name) {
        return subjectRepository.findByNameContainsIgnoreCase(name);
    }

    public List<SubjectDto> findSubjectByName(String name) {
        return findByName(name)
                .stream()
                .map(sub -> objectMapper.convertValue(sub, SubjectDto.class))
                .collect(Collectors.toList());
    }

    public Subject findBySubjectCode(String subjectCode) {
        return subjectRepository.findBySubjectCode(subjectCode)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find subject with subject code " + subjectCode));
    }

    public List<Subject> findSubjectBySubjectCode(String subjectCode) {
        return subjectRepository.findBySubjectCodeContainsIgnoreCase(subjectCode);
    }

    public List<SubjectDto> findSubjectDtoBySubjectCode(String subjectCode) {
        return findSubjectBySubjectCode(subjectCode)
                .stream()
                .map(sub -> objectMapper.convertValue(sub, SubjectDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateSubject(Long id, SubjectDto subjectDto) {
        Subject subject = findById(id);

        if (!subject.getName().equals(subjectDto.getName())) {
            isNameDuplicated(subjectDto.getName());
        }
        if (!subject.getSubjectCode().equals(subjectDto.getSubjectCode())) {
            isSubjectCodeDuplicated(subjectDto.getSubjectCode());
        }

        subject = objectMapper.convertValue(subjectDto, Subject.class);
        subject.setId(id);
        subjectRepository.save(subject);
    }

    public List<ResultDto> findResultBySubject(Long id) {
        existById(id);
        return resultRepository.findResultBySubject(id);
    }

    public List<ResultDtoTest> findResultBySubjectId(Long id) {
        existById(id);
        List<Result> results = resultRepository.findSubjectResult(id);
        System.out.println(results);
        return results
                .stream()
                .map(r -> objectMapper.convertValue(r, ResultDtoTest.class))
                .collect(Collectors.toList());
    }

    public List<ResultDtoTest> findSubjectResultById(Long id) {
        existById(id);
        return resultRepository.findSubjectResultById(id)
                .stream()
                .map(r -> objectMapper.convertValue(r, ResultDtoTest.class))
                .collect(Collectors.toList());
    }
}

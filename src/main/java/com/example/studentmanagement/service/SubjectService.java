package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.PageDto;
import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.dto.SubjectDto;
import com.example.studentmanagement.entity.Subject;
import com.example.studentmanagement.exception.ResourceAlreadyExistsException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.mapper.SubjectMapper;
import com.example.studentmanagement.repository.ResultRepository;
import com.example.studentmanagement.repository.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private SubjectRepository subjectRepository;

    public void addSubject(SubjectDto subjectDto) {
        isNameDuplicated(subjectDto.getName());
        isSubjectCodeDuplicated(subjectDto.getSubjectCode());
        subjectRepository.save(subjectMapper.dtoToEntity(subjectDto));
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

    public void deleteSubject(Long id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Subject with id " + id + " was not found");
        }
    }

    public List<SubjectDto> findAllSubjects() {
        return subjectMapper.entitiesToDtos(subjectRepository.findAll());
    }

    public List<SubjectDto> findAllSubjectsPageDto(PageDto pageDto) {
        Pageable paging = PageRequest.of(
                pageDto.getPageNo(),
                pageDto.getPageSize(),
                Sort.Direction.fromString(pageDto.getSortDir()),
                pageDto.getSortBy()
        );
        Page<Subject> pagedResult = subjectRepository.findByNameContainsIgnoreCase(pageDto.getKey(), paging);
        if (pagedResult.hasContent()) {
            return subjectMapper.entitiesToDtos(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    private Subject findById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject with id " + id + " was not found"));
    }

    public SubjectDto findSubjectById(Long id) {
        Subject subject = findById(id);
        return mapper.map(subject, SubjectDto.class);
//        return subjectMapper.entityToDto(findById(id));
    }

    private List<Subject> findByName(String name) {
        return subjectRepository.findByNameContainsIgnoreCase(name);
    }

    public List<SubjectDto> findSubjectByName(String name) {
        return subjectMapper.entitiesToDtos(findByName(name));
    }

    public Subject findBySubjectCode(String subjectCode) {
        return subjectRepository.findBySubjectCode(subjectCode)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find subject with subject code " + subjectCode));
    }

    public List<Subject> findSubjectBySubjectCode(String subjectCode) {
        return subjectRepository.findBySubjectCodeContainsIgnoreCase(subjectCode);
    }

    public List<SubjectDto> findSubjectDtoBySubjectCode(String subjectCode) {
        return subjectMapper.entitiesToDtos(findSubjectBySubjectCode(subjectCode));
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

        subject = subjectMapper.dtoToEntity(subjectDto);
        subject.setId(id);
        subjectRepository.save(subject);
    }

    public List<ResultDto> findResultBySubject(Long id) {
        findById(id);
        return resultRepository.findResultBySubject(id);
    }
}

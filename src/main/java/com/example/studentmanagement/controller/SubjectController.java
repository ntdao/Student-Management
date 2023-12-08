package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.PageDto;
import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.dto.ResultDtoTest;
import com.example.studentmanagement.dto.SubjectDto;
import com.example.studentmanagement.entity.Result;
import com.example.studentmanagement.entity.Subject;
import com.example.studentmanagement.service.ResultService;
import com.example.studentmanagement.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subjects")
@PreAuthorize("hasAuthority('ADMIN')")
public class SubjectController {
    @Autowired
    private ResultService resultService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/all")
    public List<SubjectDto> findAllSubjects() {
        return subjectService.findAllSubjects();
    }

    @PostMapping("/page")
    public List<SubjectDto> findAllStudentsPageDto(@RequestBody PageDto pageDto) {
        return subjectService.findAllSubjectsPageDto(pageDto);
    }

    @GetMapping("/find-by-id/{id}")
    public Subject findSubjectById(@PathVariable("id") Long id) {
        return subjectService.findBySubjectId(id);
    }

    @GetMapping("/find-by-subject-id/{id}")
    public Subject findById(@PathVariable("id") Long id) {
        return subjectService.findSubjectId(id);
    }

    @GetMapping("/find-jpql-by-id/{id}")
    public Subject findJPQLById(@PathVariable("id") Long id) {
        return subjectService.findById(id);
    }

    @GetMapping("/find-dto-by-id/{id}")
    public SubjectDto findDtoById(@PathVariable("id") Long id) {
        return subjectService.findSubjectById(id);
    }

    @PostMapping("/find-by-subject-code")
    public List<SubjectDto> findSubjectById(@RequestBody Map<String, String> requestParams) {
        return subjectService.findSubjectDtoBySubjectCode(requestParams.get("subjectCode"));
    }

    @PostMapping("/find-by-name")
    public List<SubjectDto> findSubjectByName(@RequestBody Map<String, String> requestParams) {
        return subjectService.findSubjectByName(requestParams.get("name"));
    }

    @GetMapping("/{id}/result-dto")
    public List<ResultDto> findResultBySubject(@PathVariable("id") Long id) {
        return subjectService.findResultBySubject(id);
    }

    @GetMapping("/{id}/result")
    public List<ResultDtoTest> findSubjectResult(@PathVariable("id") Long id) {
        return subjectService.findResultBySubjectId(id);
    }

    @GetMapping("/{id}/result-fetch")
    public List<ResultDtoTest> findSubjectResultById(@PathVariable("id") Long id) {
        return subjectService.findSubjectResultById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSubject(@RequestBody @Validated SubjectDto subject) {
        subjectService.addSubject(subject);
    }

    @PutMapping("/update/{id}")
    public void updateSubject(@PathVariable("id") Long id,
                              @RequestBody @Validated SubjectDto subject) {
        subjectService.updateSubject(id, subject);
    }

    @PostMapping("/update-result")
    public void updateResult(@RequestBody ResultDto resultDto) {
        resultService.updateResult(resultDto);
    }

    @PostMapping("/delete")
    public void deleteSubject(@RequestBody Map<String, Long> requestParams) {
        subjectService.deleteSubject(requestParams.get("id"));
    }
}

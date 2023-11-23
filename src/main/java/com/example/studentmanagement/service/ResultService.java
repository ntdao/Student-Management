package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.entity.Result;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.entity.Subject;
import com.example.studentmanagement.exception.ResourceAlreadyExistsException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.ResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ResultService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SubjectService subjectService;

    public void addResult(ResultDto resultDto) {
        Student student = studentService.findByStudentCode(resultDto.getStudentCode());
        Subject subject = subjectService.findBySubjectCode(resultDto.getSubjectCode());
        isResultDuplicated(student, subject);
        Result result = Result.builder()
                .student(student)
                .subject(subject)
                .mark(resultDto.getMark())
                .build();
        resultRepository.save(result);
    }

    public void isResultDuplicated(Student student, Subject subject) {
        if (resultRepository.existsByStudentAndSubject(student, subject)) {
          throw new ResourceAlreadyExistsException("Result already exists");
        }
    }

    public void deleteResult(Long id) {
        if (resultRepository.existsById(id)) {
            resultRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Result with id " + id + " was not found");
        }
    }

    public List<ResultDto> findAllResults() {
        List<Result> resultList = resultRepository.findAll();
        List<ResultDto> resultDtoList = new ArrayList<>();
        resultList.forEach(r -> resultDtoList.add(mapper.map(r, ResultDto.class)));
        return resultDtoList;
    }

    public List<ResultDto> findAllResultsDto() {
        return resultRepository.findAllResultsDto();
    }

    public ResultDto findById(Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Result with id " + id + " not found"));
        return mapper.map(result, ResultDto.class);
    }

    /**
     * @param resultDto (studentCode, subjectCode, mark)
     *
     * update: sửa điểm
     */
    @Transactional
    public void updateResult(ResultDto resultDto) {
        Student student = studentService.findByStudentCode(resultDto.getStudentCode());
        Subject subject = subjectService.findBySubjectCode(resultDto.getSubjectCode());

        Result result = findResultByStudentAndSubject(student, subject);

        result.setMark(resultDto.getMark());
        result.setId(result.getId());
        resultRepository.save(result);
    }

    private Result findResultByStudentAndSubject(Student student, Subject subject) {
        return resultRepository.findByStudentAndSubject(student, subject)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find result by student and subject"));
    }
}

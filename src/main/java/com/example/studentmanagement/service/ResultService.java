package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.dto.ResultDtoTest;
import com.example.studentmanagement.entity.Result;
import com.example.studentmanagement.entity.Subject;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.exception.ResourceAlreadyExistsException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.ResultRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;

    public void addResult(ResultDto resultDto) {
        User user = userService.findByUserCode(resultDto.getUserCode());
        Subject subject = subjectService.findBySubjectCode(resultDto.getSubjectCode());
        isResultDuplicated(user, subject);
        Result result = Result.builder()
                .user(user)
                .subject(subject)
                .mark(resultDto.getMark())
                .build();
        resultRepository.save(result);
    }

    public void isResultDuplicated(User user, Subject subject) {
        if (resultRepository.existsByUserAndSubject(user, subject)) {
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

        public List<ResultDtoTest> findAllResult() {
        return resultRepository.findAllResults()
                .stream()
                .map(r -> objectMapper.convertValue(r, ResultDtoTest.class))
                .collect(Collectors.toList());
    }

    public List<ResultDto> findAllResultsDto() {
        return resultRepository.findAllResultsDto();
    }

    /**
     * @param resultDto (studentCode, subjectCode, mark)
     *                  <p>
     *                  update: sửa điểm
     */
    @Transactional
    public void updateResult(ResultDto resultDto) {
        User user = userService.findByUserCode(resultDto.getUserCode());
        Subject subject = subjectService.findBySubjectCode(resultDto.getSubjectCode());

        Result result = findResultByUserAndSubject(user, subject);

        result.setMark(resultDto.getMark());
        result.setId(result.getId());
        resultRepository.save(result);
    }

    private Result findResultByUserAndSubject(User user, Subject subject) {
        return resultRepository.findByUserAndSubject(user, subject)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find result by user and subject"));
    }
}

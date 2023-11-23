package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.PageDto;
import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.dto.StudentDto;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.exception.ResourceAlreadyExistsException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.mapper.StudentMapper;
import com.example.studentmanagement.repository.ResultRepository;
import com.example.studentmanagement.repository.StudentRepository;
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
public class StudentService {
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentRepository studentRepository;

    public void addStudent(StudentDto studentDto) {
        isEmailDuplicated(studentDto.getEmail());
        isStudentCodeDuplicated(studentDto.getStudentCode());
        studentRepository.save(studentMapper.dtoToEntity(studentDto));
    }

    public void isEmailDuplicated(String email) {
        if (studentRepository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException("Email already taken");
        }
    }

    public void isStudentCodeDuplicated(String studentCode) {
        if (studentRepository.existsByStudentCode(studentCode)) {
            throw new ResourceAlreadyExistsException("Student code already taken");
        }
    }

    public void isStudentExistById(Long id) {
        if (!studentRepository.existsByIdAndDeleteFlagFalse(id)) {
            throw new ResourceNotFoundException("Student with id " + id + " not found");
        }
    }

    public void deleteStudent(Long id) {
        Student student = findById(id);
        student.setDeleteFlag(true);
        studentRepository.save(student);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findByDeleteFlagFalse();
    }

    public List<StudentDto> findAllStudentsDto() {
        return studentMapper.entitiesToDtos(findAllStudents());
    }

    public List<StudentDto> findAllStudentsPaging(Integer pageNo, Integer pageSize, String sortBy, String order) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        if (order.equals("desc")) {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }
        Page<Student> pagedResult = studentRepository.findByDeleteFlagFalse(paging);
        if (pagedResult.hasContent()) {
            return studentMapper.entitiesToDtos(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    public List<StudentDto> findAllStudentsPageDto(PageDto pageDto) {
        Pageable paging = PageRequest.of(
                pageDto.getPageNo(),
                pageDto.getPageSize(),
                Sort.Direction.fromString(pageDto.getSortDir()),
                pageDto.getSortBy()
        );
        Page<Student> pagedResult = studentRepository.findByNameContainsIgnoreCaseAndDeleteFlagFalse(pageDto.getKey(), paging);
        if (pagedResult.hasContent()) {
            return studentMapper.entitiesToDtos(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    private Student findById(Long id) {
        return studentRepository.findByIdAndDeleteFlagFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " was not found"));
    }

    public StudentDto findStudentById(Long id) {
        return studentMapper.entityToDto(findById(id));
    }

    public Student findByStudentCode(String studentCode) {
        return studentRepository.findByStudentCodeAndDeleteFlagFalse(studentCode)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find student with student code " + studentCode));
    }

    public List<Student> findStudentByStudentCode(String studentCode) {
        return studentRepository.findByStudentCodeContainsIgnoreCaseAndDeleteFlagFalse(studentCode);
    }

    public List<StudentDto> findStudentDtoByStudentCode(String studentCode) {
        return studentMapper.entitiesToDtos(findStudentByStudentCode(studentCode));
    }

    private List<Student> findByName(String name) {
        return studentRepository.findByNameContainsIgnoreCaseAndDeleteFlagFalse(name);
    }

    public List<StudentDto> findStudentByName(String name) {
        return studentMapper.entitiesToDtos(findByName(name));
    }

    public List<ResultDto> findResultByStudent(Long id) {
        isStudentExistById(id);
        return resultRepository.findResultByStudent(id);
    }

    @Transactional
    public void updateStudent(Long id, StudentDto studentDto) {
        Student student = findById(id);

        if (!student.getEmail().equals(studentDto.getEmail())) {
            isEmailDuplicated(studentDto.getEmail());
        }
        if (!student.getStudentCode().equals(studentDto.getStudentCode())) {
            isStudentCodeDuplicated(studentDto.getStudentCode());
        }

        student = studentMapper.dtoToEntity(studentDto);
        student.setId(id);
        studentRepository.save(student);
    }
}

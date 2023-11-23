package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.PageDto;
import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.dto.StudentDto;
import com.example.studentmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/all")
    public List<StudentDto> findAllStudents() {
        return studentService.findAllStudentsDto();
    }

    @PostMapping("/paging")
    public List<StudentDto> findAllStudentsPaging(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        return studentService.findAllStudentsPaging(pageNo, pageSize, sortBy, order);
    }

    @PostMapping("/page")
    public List<StudentDto> findAllStudentsPageDto(@RequestBody PageDto pageDto) {
        return studentService.findAllStudentsPageDto(pageDto);
    }

    @GetMapping("/find/{id}")
    public StudentDto findStudentById(@PathVariable("id") Long id) {
        return studentService.findStudentById(id);
    }

    @GetMapping("/find-by-student-code/{studentCode}")
    public List<StudentDto> findStudentByStudentCode(@PathVariable("studentCode") String studentCode) {
        return studentService.findStudentDtoByStudentCode(studentCode);
    }

    @GetMapping("/find")
    public List<StudentDto> findStudentByName(@RequestParam("name") String name) {
        return studentService.findStudentByName(name);
    }
    @GetMapping("/{id}/result")
    public List<ResultDto> findResultByStudent(@PathVariable("id") Long id) {
        return studentService.findResultByStudent(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStudent(@RequestBody StudentDto studentDto) {
        studentService.addStudent(studentDto);
    }

    @PutMapping("/update/{id}")
    public void updateStudent(@PathVariable("id") Long id,
                              @RequestBody @Validated StudentDto studentDto) {
        studentService.updateStudent(id, studentDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
    }
}

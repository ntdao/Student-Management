package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.PageDto;
import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.dto.ResultDtoTest;
import com.example.studentmanagement.dto.UserDto;
import com.example.studentmanagement.entity.Result;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private UserService userService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> findAllUser() {
        return userService.findAllUsers();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> findAllUsers() {
        return userService.findAllStudentDto();
    }

    @PostMapping("/page")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> findAllUsersPageDto(@RequestBody PageDto pageDto) {
        return userService.findAllUserPageDto(pageDto);
    }

    @GetMapping("/find/{id}")
    public UserDto findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/find-by-student-code/{studentCode}")
    public List<UserDto> findUserByUserCode(@PathVariable("studentCode") String studentCode) {
        return userService.findUserDtoByUserCode(studentCode);
    }

    @GetMapping("/find")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> findUserByName(@RequestParam("name") String name) {
        return userService.findUserByName(name);
    }

    @PostMapping("/find")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> findUserByName(@RequestBody Map<String, String> requestParams) {
        return userService.findUserByName(requestParams.get("name"));
    }

    @GetMapping("/{id}/result-dto")
    public List<ResultDto> findResultDtoByUser(@PathVariable("id") Long id) {
        return userService.findStudentResultDto(id);
    }

    @GetMapping("/{id}/result-map")
    public List<Map<String, Object>> findResultMapByUser(@PathVariable("id") Long id) {
        return userService.findStudentResultMap(id);
    }

    @GetMapping("/{id}/result-tuple")
    public List<Tuple> findResultTupleByUser(@PathVariable("id") Long id) {
        return userService.findStudentResultTuple(id);
    }

    @GetMapping("/{id}/result")
    public List<ResultDtoTest> findResultByUser(@PathVariable("id") Long id) {
        return userService.findResultByStudent(id);
    }

    @GetMapping("/{id}/student-result")
    public List<ResultDtoTest> findResultByStudentId(@PathVariable("id") Long id) {
        return userService.findResultByStudentId(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody @Validated User user) {
        userService.addUser(user);
    }

    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable("id") Long id,
                           @RequestBody @Validated UserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}

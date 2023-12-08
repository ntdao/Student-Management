package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.PageDto;
import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.dto.ResultDtoTest;
import com.example.studentmanagement.dto.UserDto;
import com.example.studentmanagement.entity.Result;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.enums.Role;
import com.example.studentmanagement.exception.ResourceAlreadyExistsException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.ResultRepository;
import com.example.studentmanagement.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        isEmailDuplicated(user.getEmail());
        isUserCodeDuplicated(user.getUserCode());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void isEmailDuplicated(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException("Email already taken");
        }
    }

    public void isUserCodeDuplicated(String userCode) {
        if (userCode != null && userRepository.existsByUserCode(userCode)) {
            throw new ResourceAlreadyExistsException("User code already taken");
        }
    }

    public void isUserExistById(Long id) {
        if (!userRepository.existsByIdAndDeleteFlagFalse(id)) {
            throw new ResourceNotFoundException("User with id " + id + " does not exist");
        }
    }

    public void deleteUser(Long id) {
        User user = findById(id);
        user.setDeleteFlag(true);
        userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findByDeleteFlagFalse();
    }

    public List<UserDto> findAllStudentDto() {
        return findAllUsers()
                .stream()
                .filter(u -> u.getRole().equals(Role.STUDENT))
                .map(u -> objectMapper.convertValue(u, UserDto.class))
                .collect(Collectors.toList());
    }

    public List<UserDto> findAllUserPageDto(PageDto pageDto) {
        Pageable paging = PageRequest.of(
                pageDto.getPageNo(),
                pageDto.getPageSize(),
                Sort.Direction.fromString(pageDto.getSortDir()),
                pageDto.getSortBy()
        );
        Page<User> pagedResult = userRepository.findStudentByName(pageDto.getKey(), paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent()
                    .stream()
                    .map(std -> objectMapper.convertValue(std, UserDto.class))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

//    public User findByUserId(Long id) {
//        return userRepository.findByUserId(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " does not exist"));
//    }
//
//    public UserDto findByStudentId(Long id) {
//        User user = userRepository.findByUserId(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " does not exist"));
//        System.out.println(user);
//        return objectMapper.convertValue(user, UserDto.class);
//    }

    public User findById(Long id) {
        return userRepository.findByIdAndDeleteFlagFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " does not exist"));
    }

    public UserDto findUserById(Long id) {
        return objectMapper.convertValue(findById(id), UserDto.class);
    }

    public User findByUserCode(String userCode) {
        return userRepository.findByUserCodeAndDeleteFlagFalse(userCode)
                .orElseThrow(() -> new ResourceNotFoundException("User with code " + userCode + " does not exist"));
    }

    public List<User> findUserByUserCode(String userCode) {
        return userRepository.findByUserCodeContainsIgnoreCaseAndDeleteFlagFalse(userCode);
    }

    public List<UserDto> findUserDtoByUserCode(String userCode) {
        return findUserByUserCode(userCode)
                .stream()
                .map(std -> objectMapper.convertValue(std, UserDto.class))
                .collect(Collectors.toList());
    }

    private List<User> findByName(String name) {
        return userRepository.findByNameContainsIgnoreCaseAndDeleteFlagFalse(name);
    }

    public List<UserDto> findUserByName(String name) {
        return findByName(name)
                .stream()
                .map(std -> objectMapper.convertValue(std, UserDto.class))
                .collect(Collectors.toList());
    }

    public List<ResultDto> findStudentResultDto(Long id) {
        isUserExistById(id);
        return resultRepository.findStudentResult(id);
    }

    public List<Map<String, Object>> findStudentResultMap(Long id) {
        isUserExistById(id);
        List<Map<String, Object>> resultMap = resultRepository.findResultByStudent(id);
        for (Map<String, Object> r : resultMap) {
            Set<String> set = r.keySet();
            for (String key : set) {
                System.out.println(key + " " + r.get(key));
            }
        }
        return resultMap;
    }

    public List<Tuple> findStudentResultTuple(Long id) {
        isUserExistById(id);
        List<Tuple> result = resultRepository.findResultByStudentId(id);
        for (Tuple r : result) {
            System.out.println("user_code:" + r.get("userCode"));
            System.out.println("subject_code:" +r.get("subjectCode"));
            System.out.println("mark:" +r.get("mark"));
        }
        return result;
    }

    public List<ResultDtoTest> findResultByStudent(Long id) {
        isUserExistById(id);
        List<Result> results = resultRepository.findStudentResultById(id);
        System.out.println(results);
        return results.stream()
                .map(r -> objectMapper.convertValue(r, ResultDtoTest.class))
                .collect(Collectors.toList());
    }

    public List<ResultDtoTest> findResultByStudentId(Long id) {
        isUserExistById(id);
        List<Result> results = resultRepository.findStudentResultByStudentId(id);
        System.out.println(results);
        return results.stream()
                .map(r -> objectMapper.convertValue(r, ResultDtoTest.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(Long id, UserDto userDto) {
        User user = findById(id);

        if (!user.getEmail().equals(userDto.getEmail())) {
            isEmailDuplicated(userDto.getEmail());
        }
        if (!user.getUserCode().equals(userDto.getUserCode())) {
            isUserCodeDuplicated(userDto.getUserCode());
        }

        User newUser = objectMapper.convertValue(userDto, User.class);
        newUser.setId(id);
        newUser.setPassword(user.getPassword());
        newUser.setRole(user.getRole());
        userRepository.save(newUser);
    }
}

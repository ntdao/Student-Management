package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.dto.ResultDtoTest;
import com.example.studentmanagement.entity.Result;
import com.example.studentmanagement.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/results")
@PreAuthorize("hasAuthority('ADMIN')")
public class ResultController {
    @Autowired
    private ResultService resultService;

    @GetMapping()
    public List<ResultDtoTest> findAllResult() {
        return resultService.findAllResult();
    }

    @GetMapping("/all")
    public List<ResultDto> findAllResultsDto() {
        return resultService.findAllResultsDto();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addResult(@RequestBody ResultDto result) {
        resultService.addResult(result);
    }

    @PostMapping("/delete")
    public void deleteResult(@RequestBody Map<String, Long> requestParams) {
        resultService.deleteResult(requestParams.get("id"));
    }
}

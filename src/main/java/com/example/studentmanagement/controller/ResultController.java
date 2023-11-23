package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.ResultDto;
import com.example.studentmanagement.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {
    @Autowired
    private ResultService resultService;
    @GetMapping()
    public List<ResultDto> findAllResults() {
        return resultService.findAllResults();
    }
    @GetMapping("/all")
    public List<ResultDto> findAllResultsDto() {
        return resultService.findAllResultsDto();
    }

    @GetMapping("/find/{id}")
    public ResultDto findById(@PathVariable("id") Long id) {
        return resultService.findById(id);
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

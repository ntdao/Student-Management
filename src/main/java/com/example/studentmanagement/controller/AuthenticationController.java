package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.AuthenticationResponse;
import com.example.studentmanagement.dto.AuthenticationRequest;
import com.example.studentmanagement.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody AuthenticationRequest request) {
        return authenticationService.register(request);
    }
}

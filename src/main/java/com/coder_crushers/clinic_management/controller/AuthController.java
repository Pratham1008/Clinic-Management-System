package com.coder_crushers.clinic_management.controller;

import com.coder_crushers.clinic_management.model.Patient;
import com.coder_crushers.clinic_management.response.ApiResponse;
import com.coder_crushers.clinic_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody Patient patient) {
        return ResponseEntity.ok(authService.registerUser(patient));
    }

    @PutMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody Patient patient) {
        return ResponseEntity.ok(authService.loginUser(patient));
    }

}

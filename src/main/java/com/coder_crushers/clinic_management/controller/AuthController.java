package com.coder_crushers.clinic_management.controller;

import com.coder_crushers.clinic_management.dto.AuthRequest;
import com.coder_crushers.clinic_management.dto.AuthResponse;
import com.coder_crushers.clinic_management.dto.PatientDTO;
import com.coder_crushers.clinic_management.model.Patient;
import com.coder_crushers.clinic_management.response.ApiResponse;
import com.coder_crushers.clinic_management.service.AuthService;
import com.coder_crushers.clinic_management.service.PatientService;
import com.coder_crushers.clinic_management.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtService jwtService;
    private final PatientService patientService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, AuthService authService, JwtService jwtService, PatientService patientService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtService = jwtService;
        this.patientService = patientService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody Patient patient)
    {
        authService.registerUser(patient);
        return ResponseEntity.ok(new ApiResponse("success",null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> auth(@RequestBody AuthRequest authRequest)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));

            if(authentication.isAuthenticated())
            {
                String token = jwtService.generateToken(authRequest.getEmail());
                PatientDTO patientDTO = patientService.getUserByEmail(authRequest.getEmail());
                return ResponseEntity.ok(new ApiResponse(token,patientDTO));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok(new ApiResponse("fail to authenticate",null));
    }

}

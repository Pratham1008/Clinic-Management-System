package com.coder_crushers.clinic_management.dto;

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }
    // Getter

    public String getToken() {
        return token;
    }
}

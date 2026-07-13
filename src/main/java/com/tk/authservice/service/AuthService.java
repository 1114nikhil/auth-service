package com.tk.authservice.service;

import com.tk.authservice.dto.AuthResponse;
import com.tk.authservice.dto.LoginRequest;
import com.tk.authservice.dto.RefreshTokenRequest;
import com.tk.authservice.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
}

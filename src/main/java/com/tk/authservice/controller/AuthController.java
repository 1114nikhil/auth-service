package com.tk.authservice.controller;

import com.tk.authservice.dto.AuthResponse;
import com.tk.authservice.dto.LoginRequest;
import com.tk.authservice.dto.RefreshTokenRequest;
import com.tk.authservice.service.AuthService;
import com.tk.authservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest request){
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        authService.logout();
        return ResponseEntity.ok("Logout Successful");
    }
}

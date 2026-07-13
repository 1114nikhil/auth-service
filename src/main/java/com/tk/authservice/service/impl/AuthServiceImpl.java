package com.tk.authservice.service.impl;

import com.tk.authservice.dto.AuthResponse;
import com.tk.authservice.dto.LoginRequest;
import com.tk.authservice.dto.RefreshTokenRequest;
import com.tk.authservice.dto.RegisterRequest;
import com.tk.authservice.entity.Role;
import com.tk.authservice.entity.RoleName;
import com.tk.authservice.entity.User;
import com.tk.authservice.exception.ResourceAlreadyExistsException;
import com.tk.authservice.repository.RoleRepository;
import com.tk.authservice.repository.UserRepository;
import com.tk.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        if(userRepository.existsByUsername(request.getUsername())){
            throw new ResourceAlreadyExistsException("Username already exists");
        }
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(()->new RuntimeException("Default Role not found!"));

        User user= User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .enable(true)
                .build();
        user.getRoles().add(userRole);

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        return null;
    }
}

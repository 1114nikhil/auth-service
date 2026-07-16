package com.tk.authservice.service.impl;

import com.tk.authservice.dto.AuthResponse;
import com.tk.authservice.dto.LoginRequest;
import com.tk.authservice.dto.RefreshTokenRequest;
import com.tk.authservice.dto.RegisterRequest;
import com.tk.authservice.entity.RefreshToken;
import com.tk.authservice.entity.Role;
import com.tk.authservice.entity.RoleName;
import com.tk.authservice.entity.User;
import com.tk.authservice.exception.ResourceAlreadyExistsException;
import com.tk.authservice.repository.RoleRepository;
import com.tk.authservice.repository.UserRepository;
import com.tk.authservice.security.CustomeUserDetails;
import com.tk.authservice.security.jwt.JWTService;
import com.tk.authservice.service.AuthService;
import com.tk.authservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final RefreshTokenService refreshTokenService;


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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        User user=userRepository.findByEmail(
                request.getEmail()
        ).orElseThrow();

        RefreshToken refreshToken=refreshTokenService.create(user);
        String accessToken=jwtService.generatedAccessToken(user);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken=refreshTokenService.verify(request.getRefreshToken());
        User user=refreshToken.getUser();
        String accessToken=jwtService.generatedAccessToken(user);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null||!authentication.isAuthenticated()){
            return;
        }
        CustomeUserDetails userDetails=(CustomeUserDetails) authentication.getPrincipal();
        assert userDetails != null;
        refreshTokenService.delete(userDetails.getUser());
        SecurityContextHolder.clearContext();
    }
}

package com.tk.authservice.service.impl;

import com.tk.authservice.entity.RefreshToken;
import com.tk.authservice.entity.User;
import com.tk.authservice.repository.RefreshTokenRepository;
import com.tk.authservice.security.jwt.JWTService;
import com.tk.authservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTService jwtService;

    @Override
    public RefreshToken create(User user) {
        refreshTokenRepository.deleteByUser(user);
        RefreshToken token=RefreshToken.builder()
                .token(jwtService.generatedRefreshToken())
                .expiryDate(Instant.now().plusSeconds(7*24*60*60))
                .user(user)
                .build();
        return refreshTokenRepository.save(token);
    }

    @Override
    public RefreshToken verify(String token) {
        RefreshToken refreshToken=refreshTokenRepository
                .findByToken(token)
                .orElseThrow(()->new RuntimeException("Refresh Token not found"));
        if(refreshToken.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh Token Expired");
        }
        return refreshToken;
    }

    @Override
    public void delete(User user) {
refreshTokenRepository.deleteByUser(user);
    }
}

package com.tk.authservice.service;

import com.tk.authservice.entity.RefreshToken;
import com.tk.authservice.entity.User;

public interface RefreshTokenService {
    RefreshToken create(User user);
    RefreshToken verify(String token);
    void delete(User user);
}

package com.tk.authservice.security.jwt;

import com.tk.authservice.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtClaimsService {
    public Map<String,Object> buildClaims(User user){
        Map<String,Object> claims= new HashMap<>();
        claims.put(JwtConstants.USER_ID,user.getId());
        claims.put(JwtConstants.EMAIL,user.getEmail());
        claims.put(JwtConstants.ROLE,user.getRoles().stream().map(role -> role.getName().name()).toList());
        return claims;
    }
}

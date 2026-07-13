package com.tk.authservice.config;

import com.tk.authservice.entity.Role;
import com.tk.authservice.entity.RoleName;
import com.tk.authservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner loadRoles(){
        return  args -> {
            if(roleRepository.findByName(RoleName.ROLE_ADMIN).isEmpty()){
                roleRepository.save(Role.builder().name(RoleName.ROLE_ADMIN).build());
            }
            if(roleRepository.findByName(RoleName.ROLE_USER).isEmpty()){
                roleRepository.save(Role.builder().name(RoleName.ROLE_USER).build());
            }
        };
    }
}

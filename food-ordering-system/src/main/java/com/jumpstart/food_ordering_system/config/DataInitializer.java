package com.jumpstart.food_ordering_system.config;

import com.jumpstart.food_ordering_system.entity.Role;
import com.jumpstart.food_ordering_system.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Seed CUSTOMER role if not present
        if (roleRepository.findByName("CUSTOMER").isEmpty()) {
            roleRepository.save(Role.builder().name("CUSTOMER").build());
        }

        // Seed ADMIN role if not present
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            roleRepository.save(Role.builder().name("ADMIN").build());
        }
    }
}
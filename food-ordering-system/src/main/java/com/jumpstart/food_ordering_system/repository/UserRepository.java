package com.jumpstart.food_ordering_system.repository;

import com.jumpstart.food_ordering_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Used to look up a user during login
    boolean existsByEmail(String email);     // Used to check if an email is taken during registration
}
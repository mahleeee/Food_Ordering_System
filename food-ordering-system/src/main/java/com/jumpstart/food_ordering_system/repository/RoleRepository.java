package com.jumpstart.food_ordering_system.repository;

import com.jumpstart.food_ordering_system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name); // Used to look up roles by name
}
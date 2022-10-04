package com.security.jwtsecurity.repositories;

import com.security.jwtsecurity.models.ERole;
import com.security.jwtsecurity.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

package com.security.jwtsecurity.repositories;

import com.security.jwtsecurity.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}

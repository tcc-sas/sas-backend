package com.fatec.sasbackend.repository;


import com.fatec.sasbackend.entity.Role;
import com.fatec.sasbackend.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

package org.example.crm_back.repositories;

import org.example.crm_back.entities.Manager;
import org.example.crm_back.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByEmail(String email);
    Page<Manager> findByRoleNot(Role role, Pageable pageable);
}

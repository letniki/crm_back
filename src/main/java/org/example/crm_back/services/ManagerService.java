package org.example.crm_back.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.crm_back.dto.manager.SignUpRequestDto;
import org.example.crm_back.dto.manager.SignUpResponseDto;
import org.example.crm_back.entities.Manager;
import org.example.crm_back.enums.Role;
import org.example.crm_back.repositories.ManagerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService implements UserDetailsService {
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public SignUpResponseDto createManager(@Valid SignUpRequestDto signUpRequestDto) {
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());
        Manager manager = new Manager();
        manager.setEmail(signUpRequestDto.getEmail());
        manager.setPassword(password);
        manager.setRole(Role.ROlE_MANAGER);
        managerRepository.save(manager);

        return SignUpResponseDto.builder()
                .id(manager.getId())
                .email(manager.getEmail())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return managerRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with provided email was not found"));
    }
}

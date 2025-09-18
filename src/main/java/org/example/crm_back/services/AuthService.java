package org.example.crm_back.services;

import lombok.RequiredArgsConstructor;
import org.example.crm_back.dto.manager.AuthRequestDto;
import org.example.crm_back.dto.manager.AuthResponseDto;
import org.example.crm_back.entities.Manager;
import org.example.crm_back.repositories.ManagerRepository;
import org.example.crm_back.utilities.JwtUtility;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final ManagerService managerService;
    private final ManagerRepository managerRepository;
    private final JwtUtility jwtUtility;

    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Invalid credentials");
        }

        UserDetails userDetails = managerService.loadUserByUsername(authRequestDto.getEmail());
        String accessToken = jwtUtility.generateAccessToken(userDetails);
        String refreshToken = jwtUtility.generateRefreshToken(userDetails);

        Manager manager = managerRepository.findByEmail(authRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .name(manager.getName())
                .role(manager.getRole())
                .build();
    }

    public AuthResponseDto refreshToken(String refreshToken) {
        if (jwtUtility.isTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh token expired");
        }

        String email = jwtUtility.extractUsername(refreshToken);
        UserDetails userDetails = managerService.loadUserByUsername(email);

        String newAccessToken = jwtUtility.generateAccessToken(userDetails);
        String newRefreshToken = jwtUtility.generateRefreshToken(userDetails);

        Manager manager = managerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        return AuthResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .name(manager.getName())
                .role(manager.getRole())
                .build();
    }
}

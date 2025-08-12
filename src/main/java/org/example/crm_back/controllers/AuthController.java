package org.example.crm_back.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crm_back.dto.manager.*;
import org.example.crm_back.services.ManagerService;
import org.example.crm_back.utilities.JwtUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final ManagerService managerService;
    private final JwtUtility jwtUtility;
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp( @Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok(managerService.createManager(signUpRequestDto));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody @Valid AuthRequestDto authRequestDto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
                (authRequestDto.getEmail(), authRequestDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        if (auth.isAuthenticated()) {
            UserDetails customer = managerService.loadUserByUsername(authRequestDto.getEmail());
            String accessToken = jwtUtility.generateAccessToken(customer);
            String refreshToken = jwtUtility.generateRefreshToken(customer);
            return new ResponseEntity<>(AuthResponseDto
                    .builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build(),
                    HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> getNewTokenPair(@RequestBody @Valid RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.getRefreshToken();
        try {
            if (jwtUtility.isTokenExpired(refreshToken)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            String username = jwtUtility.extractUsername(refreshToken);
            UserDetails manager = managerService.loadUserByUsername(username);

            String newAccessToken = jwtUtility.generateAccessToken(manager);
            String newRefreshToken = jwtUtility.generateRefreshToken(manager);
            return new ResponseEntity<>(AuthResponseDto.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        }
}

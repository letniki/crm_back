package org.example.crm_back.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crm_back.dto.manager.*;
import org.example.crm_back.services.AuthService;
import org.example.crm_back.services.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ManagerService managerService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp( @Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok(managerService.createManager(signUpRequestDto));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return new ResponseEntity<>(authService.authenticate(authRequestDto), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> getNewTokenPair(@RequestBody @Valid RefreshTokenRequestDto refreshTokenRequestDto) {
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequestDto.getRefreshToken()), HttpStatus.OK);

        }
}

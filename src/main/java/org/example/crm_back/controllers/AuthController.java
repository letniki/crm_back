package org.example.crm_back.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crm_back.dto.auth.ActivationRequestDto;
import org.example.crm_back.dto.auth.AuthRequestDto;
import org.example.crm_back.dto.auth.AuthResponseDto;
import org.example.crm_back.dto.auth.RefreshTokenRequestDto;
import org.example.crm_back.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return new ResponseEntity<>(authService.authenticate(authRequestDto), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> getNewTokenPair(@RequestBody @Valid RefreshTokenRequestDto refreshTokenRequestDto) {
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequestDto.getRefreshToken()), HttpStatus.OK);
        }

    @PostMapping("/setPassword/id/{id}")
    public ResponseEntity<String> generateTokenForPassword(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        String activationToken = authService.generateTokenForPassword(id, token.replace("Bearer ", ""));
        return new ResponseEntity<>(activationToken, HttpStatus.OK);
    }

    @PostMapping("/setPassword/{token}")
    public ResponseEntity<Void> setPassword(
            @PathVariable String token,
            @RequestBody ActivationRequestDto activationRequestDto) {
        authService.setPassword(token, activationRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

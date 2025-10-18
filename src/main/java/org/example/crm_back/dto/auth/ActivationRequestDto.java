package org.example.crm_back.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivationRequestDto {
    @NotBlank(message = "Password must not be empty!")
    private String password;
    @NotBlank(message = "confirmPassword must not be empty!")
    private String confirmPassword;
}

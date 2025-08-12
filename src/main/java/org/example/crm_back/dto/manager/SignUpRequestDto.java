package org.example.crm_back.dto.manager;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class SignUpRequestDto {
    @NotBlank(message = "Enter valid email")
    @Pattern(
            regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$",
            message = "Invalid email address pattern"
    )
    private String email;

    @NotBlank(message = "Enter valid password")
    @Pattern(
            regexp = "^(?=.*\\d)[A-Za-z\\d]{2,16}$",
            message = "Password must be 2-16 letters and contain at least one number"
    )
    private String password;
}

package org.example.crm_back.dto.manager;

import lombok.Builder;
import lombok.Data;
import org.example.crm_back.enums.Role;

@Data
@Builder
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private String name;
    private Role role;
}

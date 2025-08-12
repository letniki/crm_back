package org.example.crm_back.dto.manager;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResponseDto {
    private Integer id;
    private String email;
}

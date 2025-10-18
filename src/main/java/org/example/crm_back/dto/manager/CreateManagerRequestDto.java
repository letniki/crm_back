package org.example.crm_back.dto.manager;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateManagerRequestDto {
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;
}

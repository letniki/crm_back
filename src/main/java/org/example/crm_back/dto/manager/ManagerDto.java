package org.example.crm_back.dto.manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.crm_back.dto.order.StatDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {
    private Integer id;
    private String email;
    private String name;
    private String surname;
    private Boolean isActive;
    private LocalDateTime lastLogin;
    private Boolean isBanned;
    private List<StatDto> stats;
}

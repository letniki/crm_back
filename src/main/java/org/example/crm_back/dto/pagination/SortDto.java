package org.example.crm_back.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortDto {
    private String order = "id";
    private String direction = "desc";
    private Integer page = 1;
}

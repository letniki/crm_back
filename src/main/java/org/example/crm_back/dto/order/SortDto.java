package org.example.crm_back.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortDto {
    private String order = "id";
    private String direction = "desc";
    private Integer page = 1;
}

package org.example.crm_back.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponseDto<T> {
    private Long total;
    private Integer perPage;
    private Integer nextPage;
    private Integer prevPage;
    private List<T> data;
}

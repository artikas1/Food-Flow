package com.accenture.foodflow.search.dto.response;

import com.accenture.foodflow.food.dto.FoodResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponseDto {

    private List<FoodResponseDto> foods;
    private Integer currentPage;
    private Integer totalPages;
    private Long totalItems;

}

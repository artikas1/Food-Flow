package com.accenture.foodflow.search.dto.request;

import com.accenture.foodflow.food.type.Category;
import com.accenture.foodflow.food.type.FoodDetails;
import com.accenture.foodflow.search.type.SortOrder;
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
public class FoodSearchCriteriaDto {

    private String search;
    private List<Category> categories;
    private List<FoodDetails> foodDetails;
    private String sortBy;
    private SortOrder sortOrder;
    private Integer offset;
    private Integer limit;

}

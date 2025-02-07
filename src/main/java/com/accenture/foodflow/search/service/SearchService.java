package com.accenture.foodflow.search.service;

import com.accenture.foodflow.search.dto.response.PaginatedResponseDto;
import com.accenture.foodflow.search.dto.request.FoodSearchCriteriaDto;

public interface SearchService {

    PaginatedResponseDto getFood(FoodSearchCriteriaDto foodSearchCriteriaDto);

}

package com.accenture.foodflow.food.service;

import com.accenture.foodflow.food.dto.SaveFoodRequestDto;
import com.accenture.foodflow.food.dto.FoodResponseDto;

public interface FoodService {

    FoodResponseDto uploadFood(SaveFoodRequestDto saveFoodRequestDto);

}

package com.accenture.foodflow.food.integrity;

import com.accenture.foodflow.food.dto.FoodRequestDto;

import java.util.UUID;

public interface FoodDataIntegrity {

    void validateFoodRequest(FoodRequestDto foodRequestDto);
    void validateId(UUID id);

}

package com.accenture.foodflow.food.integrity;

import com.accenture.foodflow.food.dto.FoodRequestDto;
import com.accenture.foodflow.food.entity.Food;

import java.util.UUID;

public interface FoodDataIntegrity {

    void validateFoodRequest(FoodRequestDto foodRequestDto);
    void validateId(UUID id);
    void validateFood(Food food);

}

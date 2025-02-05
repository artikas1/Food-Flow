package com.accenture.foodflow.food.service;

import com.accenture.foodflow.food.dto.FoodRequestDto;
import com.accenture.foodflow.food.dto.FoodResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FoodService {

    FoodResponseDto saveFood(FoodRequestDto foodRequestDto);
    FoodResponseDto getFoodById(UUID id);
    FoodResponseDto updateFoodById(UUID id, FoodRequestDto foodRequestDto);
    void deleteFoodById(UUID id);
    Page<FoodResponseDto> getAllFoods(Pageable pageable);

}

package com.accenture.foodflow.food.mapper;

import com.accenture.foodflow.food.dto.FoodResponseDto;
import com.accenture.foodflow.food.dto.SaveFoodRequestDto;
import com.accenture.foodflow.food.entity.Food;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    public Food toEntity(SaveFoodRequestDto saveFoodRequestDto) {
        Food food = new Food();
        food.setTitle(saveFoodRequestDto.getTitle());
        food.setDescription(saveFoodRequestDto.getDescription());
        food.setCategory(saveFoodRequestDto.getCategory());
        food.setExpiryDate(saveFoodRequestDto.getExpiryDate());
        food.setFoodDetails(saveFoodRequestDto.getFoodDetails());
        food.setImage(saveFoodRequestDto.getImage());
        return food;
    }

    public FoodResponseDto toFoodResponseDto(Food food) {
        FoodResponseDto foodResponseDto = new FoodResponseDto();
        foodResponseDto.setId(food.getId());
        foodResponseDto.setTitle(food.getTitle());
        foodResponseDto.setDescription(food.getDescription());
        foodResponseDto.setCategory(food.getCategory());
        foodResponseDto.setExpiryDate(food.getExpiryDate());
        foodResponseDto.setFoodDetails(food.getFoodDetails());
        foodResponseDto.setImage(food.getImage());
        return foodResponseDto;
    }

}

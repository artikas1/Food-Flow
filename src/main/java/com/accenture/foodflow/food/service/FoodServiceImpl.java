package com.accenture.foodflow.food.service;

import com.accenture.foodflow.food.dto.FoodResponseDto;
import com.accenture.foodflow.food.dto.SaveFoodRequestDto;
import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.food.mapper.FoodMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodMapper foodMapper;

    public FoodResponseDto uploadFood(SaveFoodRequestDto saveFoodRequestDto) {
        Food food = foodMapper.toEntity(saveFoodRequestDto);
        return foodMapper.toFoodResponseDto(food);
    }
}

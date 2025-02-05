package com.accenture.foodflow.food.integrity;

import com.accenture.foodflow.common.exception.exceptions.FoodBadRequestException;
import com.accenture.foodflow.food.dto.FoodRequestDto;
import com.accenture.foodflow.food.entity.Food;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FoodDataIntegrityImpl implements FoodDataIntegrity {

    public static final String FOOD_ID_CANNOT_BE_NULL = "Food id cannot be null";
    public static final String FOOD_EXPIRY_DATE_CANNOT_BE_NULL = "Food expiry date cannot be null";
    public static final String FOOD_CATEGORY_CANNOT_BE_NULL_OR_EMPTY = "Food category cannot be null or empty";
    public static final String FOOD_TITLE_CANNOT_BE_NULL_OR_EMPTY = "Food title cannot be null or empty";
    public static final String FOOD_REQUEST_CANNOT_BE_NULL = "Food request cannot be null";
    public static final String FOOD_CANNOT_BE_NULL = "Food cannot be null";

    @Override
    public void validateFoodRequest(FoodRequestDto foodRequestDto) {
        if(foodRequestDto == null) {
            throw new FoodBadRequestException(FOOD_REQUEST_CANNOT_BE_NULL);
        }
        if(foodRequestDto.getTitle() == null || foodRequestDto.getTitle().isEmpty()) {
            throw new FoodBadRequestException(FOOD_TITLE_CANNOT_BE_NULL_OR_EMPTY);
        }
        if(foodRequestDto.getCategory() == null) {
            throw new FoodBadRequestException(FOOD_CATEGORY_CANNOT_BE_NULL_OR_EMPTY);
        }
        if(foodRequestDto.getExpiryDate() == null) {
            throw new FoodBadRequestException(FOOD_EXPIRY_DATE_CANNOT_BE_NULL);
        }
    }

    @Override
    public void validateId(UUID id) {
        if(id == null) {
            throw new FoodBadRequestException(FOOD_ID_CANNOT_BE_NULL);
        }
    }

    @Override
    public void validateFood(Food food) {
        if(food == null) {
            throw new FoodBadRequestException(FOOD_CANNOT_BE_NULL);
        }
    }


}

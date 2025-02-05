package com.accenture.foodflow.food.integrity;

import com.accenture.foodflow.common.exception.exceptions.FoodBadRequestException;
import com.accenture.foodflow.food.dto.FoodRequestDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FoodDataIntegrityImpl implements FoodDataIntegrity {

    @Override
    public void validateFoodRequest(FoodRequestDto foodRequestDto) {
        if(foodRequestDto == null) {
            throw new FoodBadRequestException("Food request cannot be null");
        }
        if(foodRequestDto.getTitle() == null || foodRequestDto.getTitle().isEmpty()) {
            throw new FoodBadRequestException("Food title cannot be null or empty");
        }
        if(foodRequestDto.getCategory() == null) {
            throw new FoodBadRequestException("Food category cannot be null or empty");
        }
        if(foodRequestDto.getExpiryDate() == null) {
            throw new FoodBadRequestException("Food expiry date cannot be null");
        }
    }

    @Override
    public void validateId(UUID id) {
        if(id == null) {
            throw new FoodBadRequestException("Food id cannot be null");
        }
    }


}

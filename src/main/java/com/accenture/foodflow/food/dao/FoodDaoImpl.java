package com.accenture.foodflow.food.dao;

import com.accenture.foodflow.common.exception.exceptions.FoodBadRequestException;
import com.accenture.foodflow.common.exception.exceptions.FoodNotFoundException;
import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.food.repository.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FoodDaoImpl implements FoodDao {

    private final FoodRepository foodRepository;

    @Override
    public Food saveFood(Food food) {
        if(food == null) {
            throw new FoodBadRequestException("Food cannot be null");
        }

        return foodRepository.save(food);
    }

    @Override
    public Food getFoodById(UUID id) {
        if(id == null) {
            throw new FoodBadRequestException("Id cannot be null");
        }

        return foodRepository.findById(id).orElseThrow(() -> new FoodNotFoundException("Food not found"));
    }

    @Override
    public void deleteFood(UUID id) {
        if(id == null) {
            throw new FoodBadRequestException("Id cannot be null");
        }

        foodRepository.deleteById(id);
    }

    @Override
    public Page<Food> getAllFoods(Pageable pageable) {
        return foodRepository.findAll(pageable);
    }
}

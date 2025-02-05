package com.accenture.foodflow.food.dao;

import com.accenture.foodflow.common.exception.exceptions.FoodNotFoundException;
import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.food.integrity.FoodDataIntegrity;
import com.accenture.foodflow.food.repository.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FoodDaoImpl implements FoodDao {

    public static final String FOOD_NOT_FOUND = "Food not found";

    private final FoodRepository foodRepository;
    private final FoodDataIntegrity foodDataIntegrity;

    @Override
    public Food saveFood(Food food) {
        foodDataIntegrity.validateFood(food);

        return foodRepository.save(food);
    }

    @Override
    public Food getFoodById(UUID id) {
        foodDataIntegrity.validateId(id);

        return foodRepository.findById(id).orElseThrow(() -> new FoodNotFoundException(FOOD_NOT_FOUND));
    }

    @Override
    public void deleteFood(UUID id) {
        foodDataIntegrity.validateId(id);

        foodRepository.deleteById(id);
    }

    @Override
    public Page<Food> getAllFoods(Pageable pageable) {
        return foodRepository.findAll(pageable);
    }
}

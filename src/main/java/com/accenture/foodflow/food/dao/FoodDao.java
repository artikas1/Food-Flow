package com.accenture.foodflow.food.dao;

import com.accenture.foodflow.food.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.UUID;

public interface FoodDao {

    Food saveFood(Food food);
    Food getFoodById(UUID id);
    void deleteFood(UUID id);
    Page<Food> getAllFoodsByUserId(Pageable pageable, UUID userId);
    Page<Food> findAll(Specification<Food> specification, Pageable pageable);

}

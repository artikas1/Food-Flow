package com.accenture.foodflow.food.repository;

import com.accenture.foodflow.food.entity.Food;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FoodRepository extends JpaRepository<Food, UUID>, JpaSpecificationExecutor<Food> {
}
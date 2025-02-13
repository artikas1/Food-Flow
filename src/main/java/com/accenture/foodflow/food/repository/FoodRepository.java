package com.accenture.foodflow.food.repository;

import com.accenture.foodflow.food.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface FoodRepository extends JpaRepository<Food, UUID>, JpaSpecificationExecutor<Food> {

    @Query("SELECT f FROM Food f WHERE f.user.id = :userId")
    Page<Food> findAllByUser(Pageable pageable, UUID userId);

}
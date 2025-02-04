package com.accenture.foodflow.food.controller;

import com.accenture.foodflow.food.dto.SaveFoodRequestDto;
import com.accenture.foodflow.food.dto.FoodResponseDto;
import com.accenture.foodflow.food.service.FoodService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/food")
@RestController
@AllArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping("/upload")
    public ResponseEntity<FoodResponseDto> uploadFood(
            @Valid @RequestBody SaveFoodRequestDto saveFoodRequestDto) {
        return ResponseEntity.ok(foodService.uploadFood(saveFoodRequestDto));
    }

}

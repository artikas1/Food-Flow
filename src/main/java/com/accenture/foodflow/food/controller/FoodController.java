package com.accenture.foodflow.food.controller;

import com.accenture.foodflow.food.dto.FoodResponseDto;
import com.accenture.foodflow.food.mapper.FoodMapper;
import com.accenture.foodflow.food.service.FoodService;
import com.accenture.foodflow.food.type.Category;
import com.accenture.foodflow.food.type.FoodDetails;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@RequestMapping("/food")
@RestController
@AllArgsConstructor
public class FoodController {

    private static final int MAX_PAGE_SIZE = 50;
    private final FoodService foodService;
    private final FoodMapper foodMapper;

    @PostMapping
    public ResponseEntity<FoodResponseDto> saveFood(@RequestParam("title") String title,
                                                    @RequestParam("description") String description,
                                                    @RequestParam(value = "category", required = false) Category category,
                                                    @RequestParam("expiryDate") LocalDate expiryDate,
                                                    @RequestParam(value = "foodDetails", required = false) FoodDetails foodDetails,
                                                    @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
            var request = foodMapper.toRequestDto(title, description, category, expiryDate, foodDetails, image);

            return ResponseEntity.status(HttpStatus.CREATED).body(foodService.saveFood(request));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponseDto> getFood(@PathVariable UUID id) {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodResponseDto> updateFood(@PathVariable UUID id,
                                                      @RequestParam(value = "title", required = false) String title,
                                                      @RequestParam(value = "description", required = false) String description,
                                                      @RequestParam(value = "category", required = false) Category category,
                                                      @RequestParam(value = "foodDetails", required = false) FoodDetails foodDetails,
                                                      @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            var request = foodMapper.toRequestDto(title, description, category, foodDetails, image);

            return ResponseEntity.ok(foodService.updateFoodById(id, request));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable UUID id) {
        foodService.deleteFoodById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<Page<FoodResponseDto>> getAllFoods(@RequestParam("page") int page,
                                                             @RequestParam("pageSize") int size) {
        invalidatePageAndSize(page, size);

        return ResponseEntity.ok(foodService.getAllFoods(PageRequest.of(page, size)));
    }

    private void invalidatePageAndSize(int page, int size) {
        if (page < 0 || size < 0 || size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Invalid page or size");
        }
    }

}

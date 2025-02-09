package com.accenture.foodflow.food.controller;

import com.accenture.foodflow.common.exception.exceptions.InvalidPageException;
import com.accenture.foodflow.food.dto.FoodResponseDto;
import com.accenture.foodflow.food.mapper.FoodMapper;
import com.accenture.foodflow.food.service.FoodService;
import com.accenture.foodflow.food.type.Category;
import com.accenture.foodflow.food.type.FoodDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@RequestMapping("/food")
@RestController
@AllArgsConstructor
@Tag(name = "Food Controller", description = "APIs for managing food items")
public class FoodController {

    private static final int MAX_PAGE_SIZE = 50;
    public static final String INVALID_PAGE_OR_SIZE = "Invalid page or size";

    private final FoodService foodService;
    private final FoodMapper foodMapper;

    @Operation(summary = "Create a new food item", description = "Saves a new food item with the provided details")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodResponseDto> saveFood(
            @Parameter(description = "Title of the food item") @RequestParam("title") String title,
            @Parameter(description = "Description of the food item") @RequestParam("description") String description,
            @Parameter(description = "Category of the food item") @RequestParam(value = "category", required = false) Category category,
            @Parameter(description = "City where the food item is located") @RequestParam("city") String city,
            @Parameter(description = "Expiry date of the food item") @RequestParam("expiryDate") LocalDate expiryDate,
            @Parameter(description = "Additional food details") @RequestParam(value = "foodDetails", required = false) FoodDetails foodDetails,
            @Parameter(description = "Image of the food item") @RequestParam("image") MultipartFile image) {
        try {
            var request = foodMapper.toRequestDto(title, description, category, city, expiryDate, foodDetails, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(foodService.saveFood(request));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get a food item by ID", description = "Retrieves a specific food item by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<FoodResponseDto> getFood(
            @Parameter(description = "ID of the food item") @PathVariable UUID id) {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }

    @Operation(summary = "Update a food item", description = "Updates an existing food item with new details")
    @PutMapping("/{id}")
    public ResponseEntity<FoodResponseDto> updateFood(
            @Parameter(description = "ID of the food item") @PathVariable UUID id,
            @Parameter(description = "New title of the food item") @RequestParam(value = "title", required = false) String title,
            @Parameter(description = "New description of the food item") @RequestParam(value = "description", required = false) String description,
            @Parameter(description = "New category of the food item") @RequestParam(value = "category", required = false) Category category,
            @Parameter(description = "Updated food details") @RequestParam(value = "foodDetails", required = false) FoodDetails foodDetails,
            @Parameter(description = "Updated image of the food item") @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            var request = foodMapper.toRequestDto(title, description, category, foodDetails, image);
            return ResponseEntity.ok(foodService.updateFoodById(id, request));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete a food item", description = "Removes a specific food item by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(
            @Parameter(description = "ID of the food item to delete") @PathVariable UUID id) {
        foodService.deleteFoodById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all food items", description = "Retrieves a paginated list of all food items")
    @GetMapping("/all")
    public ResponseEntity<Page<FoodResponseDto>> getAllFoods(
            @Parameter(description = "Page number") @RequestParam("page") int page,
            @Parameter(description = "Number of items per page") @RequestParam("pageSize") int size) {
        invalidatePageAndSize(page, size);
        return ResponseEntity.ok(foodService.getAllFoods(PageRequest.of(page, size)));
    }

    private void invalidatePageAndSize(int page, int size) {
        if (page < 0 || size < 0 || size > MAX_PAGE_SIZE) {
            throw new InvalidPageException(INVALID_PAGE_OR_SIZE);
        }
    }
}
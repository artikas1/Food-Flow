package com.accenture.foodflow.food.mapper;

import com.accenture.foodflow.food.dto.FoodRequestDto;
import com.accenture.foodflow.food.dto.FoodResponseDto;
import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.food.type.Category;
import com.accenture.foodflow.food.type.FoodDetails;
import com.accenture.foodflow.user.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class FoodMapper {

    public Food toEntity(FoodRequestDto foodRequestDto, User user) {
        return Food.builder()
                .title(foodRequestDto.getTitle())
                .description(foodRequestDto.getDescription())
                .category(foodRequestDto.getCategory())
                .createdAt(LocalDate.now())
                .expiryDate(foodRequestDto.getExpiryDate())
                .foodDetails(foodRequestDto.getFoodDetails())
                .image(foodRequestDto.getImage())
                .user(user)
                .build();
    }

    public FoodResponseDto toResponseDto(Food food) {
        return FoodResponseDto.builder()
                .id(food.getId())
                .title(food.getTitle())
                .description(food.getDescription())
                .category(food.getCategory())
                .createdAt(food.getCreatedAt())
                .expiryDate(food.getExpiryDate())
                .foodDetails(food.getFoodDetails())
                .image(food.getImage())
                .userId(food.getUser().getId())
                .build();
    }

    public FoodRequestDto toRequestDto(String title,
                                       String description,
                                       Category category,
                                       LocalDate expiryDate,
                                       FoodDetails foodDetails,
                                       MultipartFile image) throws IOException {
        return FoodRequestDto.builder()
                .title(title)
                .description(description)
                .category(category)
                .expiryDate(expiryDate)
                .foodDetails(foodDetails)
                .image(image.getBytes())
                .build();
    }

    public FoodRequestDto toRequestDto(String title,
                                       String description,
                                       Category category,
                                       FoodDetails foodDetails,
                                       MultipartFile image) throws IOException {
        return FoodRequestDto.builder()
                .title(title)
                .description(description)
                .category(category)
                .foodDetails(foodDetails)
                .image(image.getBytes())
                .build();
    }

    public List<FoodResponseDto> toDto(List<Food> foods) {
        if (foods == null || foods.isEmpty()) {
            return Collections.emptyList();
        }

        return foods.stream()
                .map(this::toResponseDto)
                .toList();
    }

}

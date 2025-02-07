package com.accenture.foodflow.food.dto;

import com.accenture.foodflow.food.type.Category;
import com.accenture.foodflow.food.type.FoodDetails;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponseDto {

    private UUID id;
    private String title;
    private String description;
    private Category category;
    private LocalDate createdAt;
    private boolean isAvailable;
    private String city;
    private LocalDate expiryDate;
    private FoodDetails foodDetails;
    private byte[] image;
    private UUID userId;

}

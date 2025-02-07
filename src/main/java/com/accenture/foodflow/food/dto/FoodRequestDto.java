package com.accenture.foodflow.food.dto;

import com.accenture.foodflow.food.type.Category;
import com.accenture.foodflow.food.type.FoodDetails;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @Nullable
    private String description;

    @NotBlank
    private String city;

    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

    @Nullable
    private FoodDetails foodDetails;

    @Nullable
    private byte[] image;
}

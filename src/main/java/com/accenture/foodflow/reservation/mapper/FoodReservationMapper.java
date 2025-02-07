package com.accenture.foodflow.reservation.mapper;

import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.reservation.dto.FoodReservationResponseDto;
import com.accenture.foodflow.reservation.entity.FoodReservation;
import com.accenture.foodflow.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FoodReservationMapper {

    public FoodReservation toEntity(Food food, User user) {
        return FoodReservation.builder()
                .food(food)
                .user(user)
                .reservationDate(LocalDate.now())
                .expirationDate(LocalDate.now().plusDays(2))
                .isCanceled(false)
                .build();
    }

    public FoodReservationResponseDto toFoodResponseDto(FoodReservation foodReservation) {
        return FoodReservationResponseDto.builder()
                .id(foodReservation.getId())
                .foodId(foodReservation.getFood().getId())
                .userId(foodReservation.getUser().getId())
                .reservationDate(foodReservation.getReservationDate())
                .expirationDate(foodReservation.getExpirationDate())
                .isCancelled(foodReservation.isCanceled())
                .build();
    }
}

package com.accenture.foodflow.reservation.controller;

import com.accenture.foodflow.reservation.dto.FoodReservationResponseDto;
import com.accenture.foodflow.reservation.service.FoodReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/reservation")
@RestController
@AllArgsConstructor
public class FoodReservationController {

    private final FoodReservationService foodReservationService;

    @PostMapping("/{foodId}")
    public ResponseEntity<FoodReservationResponseDto> reserveFood(@PathVariable UUID foodId) {
        return ResponseEntity.ok(foodReservationService.reserveFood(foodId));
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable UUID foodId) {
        foodReservationService.cancelReservation(foodId);
        return ResponseEntity.ok().build();
    }

}

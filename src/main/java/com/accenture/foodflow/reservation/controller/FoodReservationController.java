package com.accenture.foodflow.reservation.controller;

import com.accenture.foodflow.reservation.dto.FoodReservationResponseDto;
import com.accenture.foodflow.reservation.service.FoodReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Food Reservation Controller", description = "APIs for reserving and canceling food reservations")
public class FoodReservationController {

    private final FoodReservationService foodReservationService;

    @Operation(summary = "Reserve a food item", description = "Reserves a food item by its ID")
    @PostMapping("/{foodId}")
    public ResponseEntity<FoodReservationResponseDto> reserveFood(
            @Parameter(description = "ID of the food item to reserve") @PathVariable UUID foodId) {
        return ResponseEntity.ok(foodReservationService.reserveFood(foodId));
    }

    @Operation(summary = "Cancel a food reservation", description = "Cancels an existing food reservation by its ID")
    @DeleteMapping("/{foodId}")
    public ResponseEntity<Void> cancelReservation(
            @Parameter(description = "ID of the reserved food item to cancel") @PathVariable UUID foodId) {
        foodReservationService.cancelReservation(foodId);
        return ResponseEntity.ok().build();
    }

}

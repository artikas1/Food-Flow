package com.accenture.foodflow.reservation.controller;

import com.accenture.foodflow.common.exception.exceptions.InvalidPageException;
import com.accenture.foodflow.reservation.dto.FoodReservationResponseDto;
import com.accenture.foodflow.reservation.service.FoodReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/reservations")
@RestController
@AllArgsConstructor
@Tag(name = "Food Reservation Controller", description = "APIs for reserving and canceling food reservations")
public class FoodReservationController {

    private static final int MAX_PAGE_SIZE = 50;
    private static final String INVALID_PAGE_OR_SIZE = "Invalid page or size";

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

    @Operation(summary = "Get all user reservations", description = "Retrieves all reservations made by a specific user")
    @GetMapping("/user")
    public ResponseEntity<Page<FoodReservationResponseDto>> findAllUserReservations(
            @Parameter(description = "Page number") @RequestParam("page") int page,
            @Parameter(description = "Number of items per page") @RequestParam("pageSize") int size) {

        invalidatePageAndSize(page, size);
        return ResponseEntity.ok(foodReservationService.findAllUserReservations(PageRequest.of(page, size)));
    }

    @Operation(summary = "Delete reservation by food id", description = "Delete reservation by food id")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteReservationByFoodId(
            @Parameter(description = "ID of the reserved food item to delete") @RequestParam UUID foodId) {
        foodReservationService.deleteReservationByFoodId(foodId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Check reservation", description = "Check if reservation is made by currently logged in user")
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkReservation(
            @Parameter(description = "ID of the reserved food item to check") @RequestParam UUID foodId) {
        return ResponseEntity.ok(foodReservationService.checkReservation(foodId));
    }

    private void invalidatePageAndSize(int page, int size) {
        if (page < 0 || size < 0 || size > MAX_PAGE_SIZE) {
            throw new InvalidPageException(INVALID_PAGE_OR_SIZE);
        }
    }

}

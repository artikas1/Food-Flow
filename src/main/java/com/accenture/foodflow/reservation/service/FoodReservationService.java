package com.accenture.foodflow.reservation.service;

import com.accenture.foodflow.reservation.dto.FoodReservationResponseDto;

import java.util.UUID;

public interface FoodReservationService {

    FoodReservationResponseDto reserveFood(UUID foodId);
    void cancelReservation(UUID reservationId);

}

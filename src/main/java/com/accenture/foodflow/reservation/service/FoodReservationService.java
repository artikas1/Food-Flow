package com.accenture.foodflow.reservation.service;

import com.accenture.foodflow.reservation.dto.FoodReservationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FoodReservationService {

    FoodReservationResponseDto reserveFood(UUID foodId);
    void cancelReservation(UUID reservationId);
    Page<FoodReservationResponseDto> findAllUserReservations(Pageable pageable);
    Boolean checkReservation(UUID foodId);

    void deleteReservationByFoodId(UUID foodId);
}

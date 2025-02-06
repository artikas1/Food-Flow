package com.accenture.foodflow.reservation.dao;

import com.accenture.foodflow.reservation.entity.FoodReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FoodReservationDao {

    FoodReservation save(FoodReservation foodReservation);
    FoodReservation findById(UUID reservationId);
    Page<FoodReservation> findAllUserReservations(UUID userId, Pageable pageable);
    void deleteReservation(UUID reservationId);

}

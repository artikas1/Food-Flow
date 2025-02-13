package com.accenture.foodflow.reservation.repository;

import com.accenture.foodflow.reservation.entity.FoodReservation;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FoodReservationRepository extends JpaRepository<FoodReservation, UUID> {

    @Transactional
    Page<FoodReservation> findAllByUserId(UUID userId, Pageable pageable);

    Boolean existsByFoodIdAndUserId(UUID foodId, UUID userId);

    @Transactional
    void deleteFoodReservationByFood_Id(UUID foodId);

    @Transactional
    FoodReservation findFoodReservationByFood_Id(UUID foodId);

}

package com.accenture.foodflow.reservation.repository;

import com.accenture.foodflow.reservation.entity.FoodReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FoodReservationRepository extends JpaRepository<FoodReservation, UUID> {

    Page<FoodReservation> findAllByUserId(UUID userId, Pageable pageable);

}

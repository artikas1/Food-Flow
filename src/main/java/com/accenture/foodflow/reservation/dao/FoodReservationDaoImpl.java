package com.accenture.foodflow.reservation.dao;

import com.accenture.foodflow.reservation.entity.FoodReservation;
import com.accenture.foodflow.reservation.integrity.FoodReservationDataIntegrity;
import com.accenture.foodflow.reservation.repository.FoodReservationRepository;
import com.accenture.foodflow.user.integrity.UserDataIntegrity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FoodReservationDaoImpl implements FoodReservationDao {

    public static final String RESERVATION_NOT_FOUND = "Reservation not found";

    private final FoodReservationRepository foodReservationRepository;
    private final FoodReservationDataIntegrity foodReservationDataIntegrity;
    private final UserDataIntegrity userDataIntegrity;

    @Override
    public FoodReservation save(FoodReservation foodReservation) {
        foodReservationDataIntegrity.validateFoodReservation(foodReservation);

        return foodReservationRepository.save(foodReservation);
    }

    @Override
    public FoodReservation findById(UUID reservationId) {
        foodReservationDataIntegrity.validateId(reservationId);

        return foodReservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException(RESERVATION_NOT_FOUND));
    }

    @Override
    public Page<FoodReservation> findAllUserReservations(UUID userId, Pageable pageable) {
        userDataIntegrity.validateId(userId);

        return foodReservationRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public void deleteReservation(UUID reservationId) {
        foodReservationDataIntegrity.validateId(reservationId);

        foodReservationRepository.deleteById(reservationId);
    }

    @Override
    public Boolean existsByFoodIdAndId(UUID foodId, UUID userId) {
        foodReservationDataIntegrity.validateId(foodId);
        userDataIntegrity.validateId(userId);

        return foodReservationRepository.existsByFoodIdAndUserId(foodId, userId);
    }

    @Override
    public void deleteReservationByFoodId(UUID foodId) {
        foodReservationDataIntegrity.validateId(foodId);

        foodReservationRepository.deleteFoodReservationByFood_Id(foodId);
    }

    @Override
    public FoodReservation findReservationByFoodId(UUID foodId) {
        foodReservationDataIntegrity.validateId(foodId);

        return foodReservationRepository.findFoodReservationByFood_Id(foodId);
    }
}

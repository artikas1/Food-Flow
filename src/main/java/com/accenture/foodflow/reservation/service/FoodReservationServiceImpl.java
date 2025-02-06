package com.accenture.foodflow.reservation.service;

import com.accenture.foodflow.auth.service.authentication.AuthenticationService;
import com.accenture.foodflow.food.dao.FoodDao;
import com.accenture.foodflow.food.integrity.FoodDataIntegrity;
import com.accenture.foodflow.food.service.FoodService;
import com.accenture.foodflow.reservation.dao.FoodReservationDao;
import com.accenture.foodflow.reservation.dto.FoodReservationResponseDto;
import com.accenture.foodflow.reservation.integrity.FoodReservationDataIntegrity;
import com.accenture.foodflow.reservation.mapper.FoodReservationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FoodReservationServiceImpl implements FoodReservationService {

    private final FoodReservationDataIntegrity foodReservationDataIntegrity;
    private final FoodDataIntegrity foodDataIntegrity;
    private final FoodReservationDao foodReservationDao;
    private final FoodDao foodDao;
    private final FoodReservationMapper foodReservationMapper;
    private final AuthenticationService authenticationService;
    private final FoodService foodService;

    @Override
    public FoodReservationResponseDto reserveFood(UUID foodId) {
        foodDataIntegrity.validateId(foodId);

        var user = authenticationService.getAuthenticatedUser();
        var food = foodService.getFoodEntityById(foodId);

        foodReservationDataIntegrity.checkIfUserIsTheOwnerOfFood(user, food);
        foodReservationDataIntegrity.checkIfFoodIsAvailable(food);

        food.setAvailable(false);
        foodDao.saveFood(food);

        var reservation = foodReservationMapper.toEntity(food, user);

        return foodReservationMapper.toFoodResponseDto(foodReservationDao.save(reservation));
    }

    @Override
    public void cancelReservation(UUID reservationId) {
        foodReservationDataIntegrity.validateId(reservationId);

        var user = authenticationService.getAuthenticatedUser();
        var reservation = foodReservationDao.findById(reservationId);

        authenticationService.checkAuthorizationBetweenUserAndReservation(user, reservation);

        var food = foodService.getFoodEntityById(reservation.getFood().getId());
        food.setAvailable(true);
        foodDao.saveFood(food);

        foodReservationDao.deleteReservation(reservationId);
    }

}

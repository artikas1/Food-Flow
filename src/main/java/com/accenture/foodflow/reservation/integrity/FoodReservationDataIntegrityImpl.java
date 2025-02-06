package com.accenture.foodflow.reservation.integrity;

import com.accenture.foodflow.common.exception.exceptions.FoodReservationBadRequestException;
import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.reservation.entity.FoodReservation;
import com.accenture.foodflow.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FoodReservationDataIntegrityImpl implements FoodReservationDataIntegrity {

    public static final String ID_CANNOT_BE_NULL = "Food reservation id cannot be null";
    public static final String FOOD_RESERVATION_CANNOT_BE_NULL = "Food reservation cannot be null";
    public static final String USER_CANNOT_RESERVE_THEIR_OWN_FOOD = "User cannot reserve their own food";

    @Override
    public void validateId(UUID id) {
        if(id == null) {
            throw new FoodReservationBadRequestException(ID_CANNOT_BE_NULL);
        }
    }

    @Override
    public void validateFoodReservation(FoodReservation foodReservation) {
        if(foodReservation == null) {
            throw new FoodReservationBadRequestException(FOOD_RESERVATION_CANNOT_BE_NULL);
        }
    }

    @Override
    public void checkIfUserIsTheOwnerOfFood(User user, Food food) {
        if(user.equals(food.getUser())) {
            throw new FoodReservationBadRequestException(USER_CANNOT_RESERVE_THEIR_OWN_FOOD);
        }
    }

}

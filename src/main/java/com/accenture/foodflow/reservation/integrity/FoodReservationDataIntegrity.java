package com.accenture.foodflow.reservation.integrity;

import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.reservation.entity.FoodReservation;
import com.accenture.foodflow.user.entity.User;

import java.util.UUID;

public interface FoodReservationDataIntegrity {

    void validateId(UUID id);
    void validateFoodReservation(FoodReservation foodReservation);
    void checkIfFoodIsAvailable(Food food);
    void checkIfUserIsTheOwnerOfFood(User user, Food food);

}

package com.accenture.foodflow.food.service;

import com.accenture.foodflow.auth.service.authentication.AuthenticationService;
import com.accenture.foodflow.common.exception.exceptions.UserNotAuthorizedException;
import com.accenture.foodflow.food.dao.FoodDao;
import com.accenture.foodflow.food.dto.FoodRequestDto;
import com.accenture.foodflow.food.dto.FoodResponseDto;
import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.food.integrity.FoodDataIntegrity;
import com.accenture.foodflow.food.mapper.FoodMapper;
import com.accenture.foodflow.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodDao foodDao;
    private final FoodDataIntegrity foodDataIntegrity;
    private final FoodMapper foodMapper;
    private final AuthenticationService authenticationService;

    @Override
    public FoodResponseDto saveFood(FoodRequestDto foodRequestDto) {
        foodDataIntegrity.validateFoodRequest(foodRequestDto);

        var user = authenticationService.getAuthenticatedUser();

        var food = foodMapper.toEntity(foodRequestDto, user);

        return foodMapper.toResponseDto(foodDao.saveFood(food));
    }

    @Override
    public FoodResponseDto getFoodById(UUID id) {
        foodDataIntegrity.validateId(id);

        return foodMapper.toResponseDto(foodDao.getFoodById(id));
    }

    @Override
    public FoodResponseDto updateFoodById(UUID id, FoodRequestDto foodRequestDto) {
        foodDataIntegrity.validateId(id);

        var user = authenticationService.getAuthenticatedUser();
        var food = foodDao.getFoodById(id);

        checkAuthorization(user, food);

        food.setTitle(foodRequestDto.getTitle());
        food.setDescription(foodRequestDto.getDescription());
        food.setFoodDetails(foodRequestDto.getFoodDetails());
        food.setCategory(foodRequestDto.getCategory());
        food.setImage(foodRequestDto.getImage());

        return foodMapper.toResponseDto(foodDao.saveFood(food));
    }

    @Override
    public void deleteFoodById(UUID id) {
        foodDataIntegrity.validateId(id);

        var user = authenticationService.getAuthenticatedUser();
        var food = foodDao.getFoodById(id);

        checkAuthorization(user, food);

        foodDao.deleteFood(id);
    }

    @Override
    public Page<FoodResponseDto> getAllFoods(Pageable pageable) {
        var foods = foodDao.getAllFoods(pageable);

        return foods.map(foodMapper::toResponseDto);
    }

    private void checkAuthorization(User user, Food food) {
        if (!food.getUser().getId().equals(user.getId())) {
            throw new UserNotAuthorizedException("You are not authorized to perform this action");
        }
    }

}

package com.accenture.foodflow.food.service;

import com.accenture.foodflow.auth.service.authentication.AuthenticationService;
import com.accenture.foodflow.common.exception.exceptions.FoodBadRequestException;
import com.accenture.foodflow.food.dao.FoodDao;
import com.accenture.foodflow.food.dto.FoodRequestDto;
import com.accenture.foodflow.food.dto.FoodResponseDto;
import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.food.integrity.FoodDataIntegrity;
import com.accenture.foodflow.food.mapper.FoodMapper;
import com.accenture.foodflow.food.type.Category;
import com.accenture.foodflow.user.entity.User;
import com.accenture.foodflow.user.type.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodServiceTests {

    @Mock
    private FoodDao foodDao;

    @Mock
    private FoodDataIntegrity foodDataIntegrity;

    @Mock
    private FoodMapper foodMapper;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private FoodServiceImpl foodService;

    private User user;
    private Food food;
    private FoodRequestDto foodRequestDto;
    private FoodResponseDto foodResponseDto;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(UUID.randomUUID())
                .email("email")
                .password("password")
                .firstName("firstName")
                .lastName("lastName")
                .birthDate(LocalDate.now())
                .gender(Gender.MALE)
                .build();

        food = Food.builder()
                .id(UUID.randomUUID())
                .title("title")
                .description("description")
                .expiryDate(LocalDate.now().plusMonths(1))
                .createdAt(LocalDate.now())
                .city("city")
                .category(Category.FRUITS)
                .foodDetails(null)
                .user(user)
                .image(null)
                .build();

        foodRequestDto = FoodRequestDto.builder()
                .title("title")
                .description("description")
                .expiryDate(LocalDate.now().plusMonths(1))
                .category(Category.FRUITS)
                .city("city")
                .foodDetails(null)
                .image(null)
                .build();

        foodResponseDto = FoodResponseDto.builder()
                .id(food.getId())
                .title(food.getTitle())
                .description(food.getDescription())
                .expiryDate(food.getExpiryDate())
                .city(food.getCity())
                .createdAt(food.getCreatedAt())
                .category(food.getCategory())
                .foodDetails(food.getFoodDetails())
                .image(food.getImage())
                .build();

    }

    @Test
    void testSaveFood() {
        when(authenticationService.getAuthenticatedUser()).thenReturn(user);
        when(foodMapper.toEntity(foodRequestDto, user)).thenReturn(food);
        when(foodDao.saveFood(food)).thenReturn(food);
        when(foodMapper.toResponseDto(food)).thenReturn(foodResponseDto);

        var savedFood = foodService.saveFood(foodRequestDto);

        assertNotNull(savedFood);
        assertEquals(foodResponseDto, savedFood);

        verify(foodDataIntegrity).validateFoodRequest(foodRequestDto);
        verify(authenticationService).getAuthenticatedUser();
        verify(foodMapper).toEntity(foodRequestDto, user);
        verify(foodDao).saveFood(food);
        verify(foodMapper).toResponseDto(food);
    }

    @Test
    void testSaveFoodWhenFoodRequestDtoIsNull() {
        doThrow(FoodBadRequestException.class).when(foodDataIntegrity).validateFoodRequest(null);

        assertThrows(FoodBadRequestException.class, () -> foodService.saveFood(null));

        verify(foodDataIntegrity).validateFoodRequest(null);
        verify(authenticationService, never()).getAuthenticatedUser();
        verify(foodMapper, never()).toEntity(null, null);
        verify(foodDao, never()).saveFood(null);
        verify(foodMapper, never()).toResponseDto(null);
    }

    @Test
    void testGetFoodById() {
        var id = food.getId();
        when(foodDao.getFoodById(id)).thenReturn(food);
        when(foodMapper.toResponseDto(food)).thenReturn(foodResponseDto);

        var foundFood = foodService.getFoodById(id);

        assertNotNull(foundFood);
        assertEquals(foodResponseDto, foundFood);

        verify(foodDataIntegrity).validateId(id);
        verify(foodDao).getFoodById(id);
        verify(foodMapper).toResponseDto(food);
    }

    @Test
    void testGetFoodByIdWhenIdIsNull() {
        doThrow(FoodBadRequestException.class).when(foodDataIntegrity).validateId(null);

        assertThrows(FoodBadRequestException.class, () -> foodService.getFoodById(null));

        verify(foodDataIntegrity).validateId(null);
        verify(foodDao, never()).getFoodById(null);
        verify(foodMapper, never()).toResponseDto(null);
    }

    @Test
    void testUpdateFoodById() {
        var id = food.getId();
        when(authenticationService.getAuthenticatedUser()).thenReturn(user);
        when(foodDao.getFoodById(id)).thenReturn(food);
        when(foodDao.saveFood(food)).thenReturn(food);
        when(foodMapper.toResponseDto(food)).thenReturn(foodResponseDto);

        var updatedFood = foodService.updateFoodById(id, foodRequestDto);

        assertNotNull(updatedFood);
        assertEquals(foodResponseDto, updatedFood);

        verify(foodDataIntegrity).validateId(id);
        verify(authenticationService).getAuthenticatedUser();
        verify(foodDao).getFoodById(id);
        verify(foodMapper).toResponseDto(food);
    }

    @Test
    void testUpdateFoodByIdWhenIdIsNull() {
        doThrow(FoodBadRequestException.class).when(foodDataIntegrity).validateId(null);

        assertThrows(FoodBadRequestException.class, () -> foodService.updateFoodById(null, foodRequestDto));

        verify(foodDataIntegrity).validateId(null);
        verify(authenticationService, never()).getAuthenticatedUser();
        verify(foodDao, never()).getFoodById(null);
        verify(foodMapper, never()).toResponseDto(null);
    }

    @Test
    void testDeleteFoodById() {
        var id = food.getId();
        when(authenticationService.getAuthenticatedUser()).thenReturn(user);
        when(foodDao.getFoodById(id)).thenReturn(food);

        foodService.deleteFoodById(id);

        verify(foodDataIntegrity).validateId(id);
        verify(authenticationService).getAuthenticatedUser();
        verify(foodDao).getFoodById(id);
        verify(foodDao).deleteFood(id);
    }

    @Test
    void deleteFoodByIdWhenIdIsNull() {
        doThrow(FoodBadRequestException.class).when(foodDataIntegrity).validateId(null);

        assertThrows(FoodBadRequestException.class, () -> foodService.deleteFoodById(null));

        verify(foodDataIntegrity).validateId(null);
        verify(authenticationService, never()).getAuthenticatedUser();
        verify(foodDao, never()).getFoodById(null);
        verify(foodDao, never()).deleteFood(null);
    }

    @Test
    void testGetAllFoods() {
        var pageable = PageRequest.of(0, 10);
        var foodsPage = new PageImpl<>(List.of(food), pageable, 1);

        when(foodDao.getAllFoods(pageable)).thenReturn(foodsPage);
        when(foodMapper.toResponseDto(food)).thenReturn(foodResponseDto);

        var foods = foodService.getAllFoods(pageable);

        assertNotNull(foods);
        assertEquals(1, foods.getTotalElements());

        verify(foodDao).getAllFoods(pageable);
        verify(foodMapper).toResponseDto(food);
    }

}

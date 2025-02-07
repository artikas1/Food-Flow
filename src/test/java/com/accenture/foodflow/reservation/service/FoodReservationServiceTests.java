package com.accenture.foodflow.reservation.service;

import com.accenture.foodflow.auth.service.authentication.AuthenticationService;
import com.accenture.foodflow.common.exception.exceptions.FoodBadRequestException;
import com.accenture.foodflow.food.dao.FoodDao;
import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.food.integrity.FoodDataIntegrity;
import com.accenture.foodflow.food.service.FoodService;
import com.accenture.foodflow.food.type.Category;
import com.accenture.foodflow.reservation.dao.FoodReservationDao;
import com.accenture.foodflow.reservation.dto.FoodReservationResponseDto;
import com.accenture.foodflow.reservation.entity.FoodReservation;
import com.accenture.foodflow.reservation.integrity.FoodReservationDataIntegrity;
import com.accenture.foodflow.reservation.mapper.FoodReservationMapper;
import com.accenture.foodflow.user.entity.User;
import com.accenture.foodflow.user.type.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodReservationServiceTests {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private FoodReservationDataIntegrity foodReservationDataIntegrity;

    @Mock
    private FoodService foodService;

    @Mock
    private FoodDataIntegrity foodDataIntegrity;

    @Mock
    private FoodDao foodDao;

    @Mock
    private FoodReservationMapper foodReservationMapper;

    @Mock
    private FoodReservationDao foodReservationDao;

    @InjectMocks
    private FoodReservationServiceImpl foodReservationService;

    private User user;
    private Food food;
    private FoodReservationResponseDto foodReservationResponseDto;
    private FoodReservation foodReservation;

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

        foodReservation = FoodReservation.builder()
                .id(UUID.randomUUID())
                .food(food)
                .user(user)
                .reservationDate(LocalDate.now())
                .expirationDate(LocalDate.now().plusDays(2))
                .isCanceled(false)
                .build();

        foodReservationResponseDto = FoodReservationResponseDto.builder()
                .id(UUID.randomUUID())
                .foodId(food.getId())
                .userId(user.getId())
                .reservationDate(LocalDate.now())
                .expirationDate(LocalDate.now().plusDays(2))
                .isCancelled(false)
                .build();

    }

    @Test
    void testSaveFoodReservation() {
        doNothing().when(foodDataIntegrity).validateId(food.getId());
        when(authenticationService.getAuthenticatedUser()).thenReturn(user);
        when(foodService.getFoodEntityById(food.getId())).thenReturn(food);
        doNothing().when(foodReservationDataIntegrity).checkIfUserIsTheOwnerOfFood(user, food);
        doNothing().when(foodReservationDataIntegrity).checkIfFoodIsAvailable(food);
        when(foodDao.saveFood(food)).thenReturn(food);
        when(foodReservationMapper.toEntity(food, user)).thenReturn(foodReservation);
        when(foodReservationDao.save(foodReservation)).thenReturn(foodReservation);
        when(foodReservationMapper.toFoodResponseDto(foodReservation)).thenReturn(foodReservationResponseDto);

        var savedFoodReservation = foodReservationService.reserveFood(food.getId());

        assertNotNull(savedFoodReservation);
        assertEquals(foodReservationResponseDto, savedFoodReservation);

        verify(authenticationService).getAuthenticatedUser();
        verify(foodService).getFoodEntityById(food.getId());
        verify(foodReservationDataIntegrity).checkIfUserIsTheOwnerOfFood(user, food);
        verify(foodReservationDataIntegrity).checkIfFoodIsAvailable(food);
        verify(foodDao).saveFood(food);
        verify(foodReservationMapper).toEntity(food, user);
        verify(foodReservationDao).save(foodReservation);
        verify(foodReservationMapper).toFoodResponseDto(foodReservation);
    }

    @Test
    void testSaveFoodReservationWhenFoodIdIsNull() {
        doThrow(FoodBadRequestException.class).when(foodDataIntegrity).validateId(null);

        assertThrows(FoodBadRequestException.class, () -> foodReservationService.reserveFood(null));

        verify(foodDataIntegrity).validateId(null);
        verify(authenticationService, never()).getAuthenticatedUser();
        verify(foodService, never()).getFoodEntityById(null);
        verify(foodReservationDataIntegrity, never()).checkIfUserIsTheOwnerOfFood(null, null);
        verify(foodReservationDataIntegrity, never()).checkIfFoodIsAvailable(null);
        verify(foodDao, never()).saveFood(null);
        verify(foodReservationMapper, never()).toEntity(null, null);
        verify(foodReservationDao, never()).save(null);
        verify(foodReservationMapper, never()).toFoodResponseDto(null);
    }

}

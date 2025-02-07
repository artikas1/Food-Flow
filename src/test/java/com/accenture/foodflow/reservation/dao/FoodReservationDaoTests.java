package com.accenture.foodflow.reservation.dao;

import com.accenture.foodflow.common.exception.exceptions.FoodReservationNotFoundException;
import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.food.type.Category;
import com.accenture.foodflow.reservation.entity.FoodReservation;
import com.accenture.foodflow.reservation.integrity.FoodReservationDataIntegrity;
import com.accenture.foodflow.reservation.integrity.FoodReservationDataIntegrityImpl;
import com.accenture.foodflow.user.dao.UserDao;
import com.accenture.foodflow.user.dao.UserDaoImpl;
import com.accenture.foodflow.user.entity.User;
import com.accenture.foodflow.user.integrity.UserDataIntegrityImpl;
import com.accenture.foodflow.user.type.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({FoodReservationDaoImpl.class, UserDaoImpl.class, FoodReservationDataIntegrityImpl.class, UserDataIntegrityImpl.class})
class FoodReservationDaoTests {

    @Autowired
    private FoodReservationDao foodReservationDao;

    @Autowired
    private UserDao userDao;

    private FoodReservation foodReservation;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .email("email")
                .password("password")
                .firstName("firstName")
                .lastName("lastName")
                .birthDate(LocalDate.now())
                .gender(Gender.MALE)
                .build();
        userDao.saveUser(user);

        var date = LocalDate.now();
        Food food = Food.builder()
                .title("title")
                .description("description")
                .expiryDate(date)
                .city("city")
                .createdAt(LocalDate.now())
                .category(Category.FRUITS)
                .foodDetails(null)
                .user(user)
                .image(null)
                .build();

        foodReservation = FoodReservation.builder()
                .food(food)
                .user(user)
                .reservationDate(LocalDate.now())
                .expirationDate(LocalDate.now().plusDays(2))
                .isCanceled(false)
                .build();
    }

    @Test
    void saveFoodReservation() {
        var savedFoodReservation = foodReservationDao.save(foodReservation);

        assertNotNull(savedFoodReservation.getId());
        assertEquals(foodReservation.getFood().getId(), savedFoodReservation.getFood().getId());
        assertEquals(foodReservation.getUser().getId(), savedFoodReservation.getUser().getId());
        assertEquals(foodReservation.getReservationDate(), savedFoodReservation.getReservationDate());
        assertEquals(foodReservation.getExpirationDate(), savedFoodReservation.getExpirationDate());
        assertEquals(foodReservation.isCanceled(), savedFoodReservation.isCanceled());
    }

    @Test
    void testFindFoodReservationById() {
        var savedFoodReservation = foodReservationDao.save(foodReservation);

        var foundFoodReservation = foodReservationDao.findById(savedFoodReservation.getId());

        assertNotNull(foundFoodReservation);
        assertEquals(savedFoodReservation.getId(), foundFoodReservation.getId());
        assertEquals(savedFoodReservation.getFood().getId(), foundFoodReservation.getFood().getId());
        assertEquals(savedFoodReservation.getUser().getId(), foundFoodReservation.getUser().getId());
        assertEquals(savedFoodReservation.getReservationDate(), foundFoodReservation.getReservationDate());
        assertEquals(savedFoodReservation.getExpirationDate(), foundFoodReservation.getExpirationDate());
        assertEquals(savedFoodReservation.isCanceled(), foundFoodReservation.isCanceled());
    }

    @Test
    void testDeleteFoodReservation() {
        var foodReservationDaoMock = Mockito.mock(FoodReservationDao.class);
        var savedFood = foodReservationDao.save(foodReservation);
        foodReservationDaoMock.deleteReservation(savedFood.getId());

        doThrow(new FoodReservationNotFoundException("Food reservation not found"))
                .when(foodReservationDaoMock).findById(savedFood.getId());
    }

}

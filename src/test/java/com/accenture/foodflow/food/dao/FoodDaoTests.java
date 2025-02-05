package com.accenture.foodflow.food.dao;

import com.accenture.foodflow.common.exception.exceptions.FoodNotFoundException;
import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.food.type.Category;
import com.accenture.foodflow.user.dao.UserDao;
import com.accenture.foodflow.user.dao.UserDaoImpl;
import com.accenture.foodflow.user.entity.User;
import com.accenture.foodflow.user.type.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({FoodDaoImpl.class, UserDaoImpl.class})
class FoodDaoTests {

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private UserDao userDao;

    private Food food;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("email")
                .password("password")
                .firstName("firstName")
                .lastName("lastName")
                .birthDate(LocalDate.now())
                .gender(Gender.MALE)
                .build();
        userDao.saveUser(user);

        var date = LocalDate.now();
        food = Food.builder()
                .title("title")
                .description("description")
                .expiryDate(date)
                .createdAt(LocalDate.now())
                .category(Category.FRUITS)
                .foodDetails(null)
                .user(user)
                .image(null)
                .build();
    }

    @Test
    void testSaveFood() {
        var savedFood = foodDao.saveFood(food);

        assertNotNull(savedFood);
        assertEquals(food.getTitle(), savedFood.getTitle());
        assertEquals(food.getDescription(), savedFood.getDescription());
        assertEquals(food.getExpiryDate(), savedFood.getExpiryDate());
        assertEquals(food.getCreatedAt(), savedFood.getCreatedAt());
        assertEquals(food.getCategory(), savedFood.getCategory());
        assertEquals(food.getFoodDetails(), savedFood.getFoodDetails());
        assertEquals(food.getUser(), savedFood.getUser());
        assertEquals(food.getImage(), savedFood.getImage());
    }

    @Test
    void testFindFoodById() {
        var savedFood = foodDao.saveFood(food);
        var foundFood = foodDao.getFoodById(savedFood.getId());

        assertNotNull(foundFood);
        assertEquals(savedFood.getId(), foundFood.getId());
        assertEquals(savedFood.getTitle(), foundFood.getTitle());
        assertEquals(savedFood.getDescription(), foundFood.getDescription());
        assertEquals(savedFood.getExpiryDate(), foundFood.getExpiryDate());
        assertEquals(savedFood.getCreatedAt(), foundFood.getCreatedAt());
        assertEquals(savedFood.getCategory(), foundFood.getCategory());
        assertEquals(savedFood.getFoodDetails(), foundFood.getFoodDetails());
        assertEquals(savedFood.getUser(), foundFood.getUser());
        assertEquals(savedFood.getImage(), foundFood.getImage());
    }

    @Test
    void deleteFood() {
        var foodDaoMock = Mockito.mock(FoodDao.class);
        var savedFood = foodDao.saveFood(food);
        foodDaoMock.deleteFood(savedFood.getId());

        doThrow(new FoodNotFoundException("Food not found"))
                .when(foodDaoMock).getFoodById(savedFood.getId());
    }

    @Test
    void getAllFoods() {
        var foodDaoMock = Mockito.mock(FoodDao.class);
        var savedFood = foodDao.saveFood(food);
        var foodsPage = new PageImpl<>(List.of(savedFood), PageRequest.of(0, 10), 1);

        when(foodDaoMock.getAllFoods(PageRequest.of(0, 10))).thenReturn(foodsPage);

        var foods = foodDaoMock.getAllFoods(PageRequest.of(0, 10));

        assertNotNull(foods);
        assertEquals(1, foods.getTotalElements());
        assertEquals(savedFood.getId(), foods.getContent().get(0).getId());
        assertEquals(savedFood.getTitle(), foods.getContent().get(0).getTitle());
        assertEquals(savedFood.getDescription(), foods.getContent().get(0).getDescription());
        assertEquals(savedFood.getExpiryDate(), foods.getContent().get(0).getExpiryDate());
        assertEquals(savedFood.getCreatedAt(), foods.getContent().get(0).getCreatedAt());
        assertEquals(savedFood.getCategory(), foods.getContent().get(0).getCategory());
        assertEquals(savedFood.getFoodDetails(), foods.getContent().get(0).getFoodDetails());
        assertEquals(savedFood.getUser(), foods.getContent().get(0).getUser());
        assertEquals(savedFood.getImage(), foods.getContent().get(0).getImage());
    }

}

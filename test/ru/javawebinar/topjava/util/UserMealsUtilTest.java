package ru.javawebinar.topjava.util;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Maria on 27.11.2015.
 */
public class UserMealsUtilTest {
    List<UserMeal> mealList;

    @Before
    public void setUp() throws Exception {
        mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),

                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
    }

    @Test
    public void testListSize() throws Exception {
        List<UserMealWithExceed> list = UserMealsUtil.getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        assertEquals(2, list.size());

    }

    @Test
    public void testGetExceedTwoDay() throws Exception {
        List<UserMealWithExceed> list = UserMealsUtil.getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        assertEquals(true, list.get(1).isExceed());

    }

    @Test
    public void testGetExceedFirstDay() throws Exception {
        List<UserMealWithExceed> list = UserMealsUtil.getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        assertEquals(false, list.get(0).isExceed());

    }

    @Test
    public void testGetDescription() throws Exception {
        List<UserMealWithExceed> list = UserMealsUtil.getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        assertEquals("Завтрак", list.get(0).getDescription());
    }


    @Test
    public void testGetDateFirstDay() throws Exception {
        List<UserMealWithExceed> list = UserMealsUtil.getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        assertEquals(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), list.get(0).getDateTime());

    }

    @Test
    public void testGetDateTwoDay() throws Exception {
        List<UserMealWithExceed> list = UserMealsUtil.getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        assertEquals(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), list.get(1).getDateTime());

    }

    @Test
    public void testGetSorted() throws Exception {
        List<UserMealWithExceed> list = UserMealsUtil.getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        assertEquals(true, (list.get(0).getDateTime().compareTo(list.get(1).getDateTime()) < 0));

    }

}
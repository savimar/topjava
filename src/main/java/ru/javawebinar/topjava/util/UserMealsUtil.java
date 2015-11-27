package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Stream<UserMeal> streamMealFilter = mealList.stream();
        List<UserMealWithExceed> userMealWithExceeds =   streamMealFilter
                .filter((m) -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .map((s) -> new UserMealWithExceed(s.getDateTime(), s.getDescription(), s.getCalories(),
                        (getAlCaloriesForDay(mealList, s.getDateTime().toLocalDate(), caloriesPerDay))))
                .collect(Collectors.toList());
           //     .collect(Collectors.groupingBy(UserMealWithExceed::getDateTime.);

        return userMealWithExceeds;
    }



/*

        for (UserMeal userMeal: mealList) {
              if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                 LocalDate date= userMeal.getDateTime().toLocalDate();
                 boolean isExeed = getAlCaloriesForDay(mealList, caloriesPerDay);
                */
/* for (UserMeal user : mealList) {
                     if (user.getDateTime().toLocalDate().equals(date)) {
                         count += user.getCalories();
                     }
                 }
                 if (count > caloriesPerDay){
                     isExeed = true;
                 }
                 *//*

                userMealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), isExeed));
                 System.out.println(userMeal.getDateTime() + " " + userMeal.getDescription() + " " +  userMeal.getCalories()+ " " + isExeed);
         }

        }
        return userMealWithExceeds;
    }
*/


    public static boolean getAlCaloriesForDay(List<UserMeal> list, LocalDate date, int caloriesPerDay) {

        int count = list.stream()
                .filter(user -> user.getDateTime().toLocalDate().equals(date))
                .mapToInt(UserMeal::getCalories)
                .sum();
      /*  for (UserMeal userMeal : list) {
            count += userMeal.getCalories();
        }
        */
        //    list.forEach(n -> );
        if (count > caloriesPerDay)
            return true;
        else
            return false;
    }
}

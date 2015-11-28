package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

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
        Map<LocalDate, Boolean> mapCalories = getListDateUnique(mealList)
                .stream()
                .collect(Collectors.toMap(
                        (p -> p),
                        p -> getAllCaloriesForDay(mealList, p, caloriesPerDay)));

        List<UserMealWithExceed> userMealWithExceeds = mealList
                .stream()
                .filter((m) -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .map((s) -> new UserMealWithExceed(s.getDateTime(), s.getDescription(), s.getCalories(),
                        (mapCalories.get(s.getDateTime().toLocalDate()))))
                .sorted((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()))
                .collect(Collectors.toList());


        return userMealWithExceeds;
    }



/*

        for (UserMeal userMeal: mealList) {
              if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                 LocalDate date= userMeal.getDateTime().toLocalDate();
              //   boolean isExeed = getAllCaloriesForDay(mealList, caloriesPerDay);
                    for (UserMeal user : mealList) {
                     if (user.getDateTime().toLocalDate().equals(date)) {
                         count += user.getCalories();
                     }
                 }
                 if (count > caloriesPerDay){
                     isExeed = true;
                 }
                userMealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), isExeed));
                 System.out.println(userMeal.getDateTime() + " " + userMeal.getDescription() + " " +  userMeal.getCalories()+ " " + isExeed);
         }

        }
        return userMealWithExceeds;
    }
*/


    public static boolean getAllCaloriesForDay(List<UserMeal> list, LocalDate date, int caloriesPerDay) {

        int count = list.stream()
                .filter(user -> user.getDateTime().toLocalDate().equals(date))
                .mapToInt(UserMeal::getCalories)
                .sum();

        return count > caloriesPerDay;

    }

    public static List<LocalDate> getListDateUnique(List<UserMeal> mealList) {
        List<LocalDate> list = mealList.stream()
                .map((m) -> m.getDateTime().toLocalDate())
                .unordered()
                .distinct()
                .collect(Collectors.toList());
        return list;

    }


}

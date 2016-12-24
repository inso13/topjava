package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2015, 5, 29, 0,0);
    public static final LocalDateTime END_DATE_TIME = LocalDateTime.of(2015, 5, 30, 23,0);
    private static final int MEAL_ID = START_SEQ+2;

    public static final Meal MEAL1 = new Meal(MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL_ID+1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(MEAL_ID+2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(MEAL_ID+3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL5 = new Meal(MEAL_ID+4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL6 = new Meal(MEAL_ID+5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final Meal ADMIN_MEAL1 = new Meal(MEAL_ID+6, LocalDateTime.of(2015, Month.JUNE, 10, 11, 0), "Завтрак", 500);
    public static final Meal ADMIN_MEAL2 = new Meal(MEAL_ID+7, LocalDateTime.of(2015, Month.JUNE, 11, 14, 0), "Обед", 3000);


    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected==actual || (Objects.equals(expected.toString(), actual.toString()))
    );

}

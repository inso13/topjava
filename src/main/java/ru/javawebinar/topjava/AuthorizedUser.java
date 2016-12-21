package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;

/**
 * GKislin
 * 06.03.2015.
 */
public class AuthorizedUser {

    private static int userId;


    public static void setUserId(int userId)
    {
        AuthorizedUser.userId = userId;
    }

    public static int id() {
        return userId;
    }

    public static int getCaloriesPerDay() {
        return MealsUtil.DEFAULT_CALORIES_PER_DAY;
    }

}

package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;

/**
 * GKislin
 * 06.03.2015.
 */
public class AuthorizedUser {

    private static int userId;


    private static String name;

    public static void setName(String name) {
        AuthorizedUser.name = name;
    }

    public static void setUserId(int userId)
    {
        AuthorizedUser.userId = userId;
        if (userId==1)
       setName("User");
        else if (userId==2)
            setName("Admin");
        else setName("Unknown user");
    }

    public static int id() {
        return userId;
    }

    public static int getCaloriesPerDay() {
        return MealsUtil.DEFAULT_CALORIES_PER_DAY;
    }

    public static String getName() {
        return name;
    }
}

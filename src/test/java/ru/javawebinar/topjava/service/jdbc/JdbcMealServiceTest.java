package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

/**
 * Created by Inso on 13.01.2017.
 */
@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.JDBC})
public class JdbcMealServiceTest extends MealServiceTest {
}

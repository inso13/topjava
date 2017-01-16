package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by Котик on 16.01.2017.
 */
@Repository
@Profile(Profiles.POSTGRES)
public class JdbcMealRepositoryImplPostgr extends JdbcMealRepositoryImpl {

    public JdbcMealRepositoryImplPostgr(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("date_time", meal.getDateTime())
                .addValue("user_id", userId);

        if (checkNew(meal, map)) return null;
        return meal;
    }




    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, startDate, endDate);
    }
}

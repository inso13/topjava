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
@Profile(Profiles.HSQLDB)
public class JdbcMealRepositoryImplHsql extends JdbcMealRepositoryImpl {

    public JdbcMealRepositoryImplHsql(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public <T> T convert(LocalDateTime dateTime) {
        Date out = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        return (T) out;
    }
}

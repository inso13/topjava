package ru.javawebinar.topjava.repository;


import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.Profiles;


@Profile({Profiles.JDBC})
public class JdbcUtil extends BaseUtil {

    public void clear2ndLevelHibernateCache() {}
}

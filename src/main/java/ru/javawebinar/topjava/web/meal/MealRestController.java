package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;

    public Meal save(Meal meal)
    {
        int userId = AuthorizedUser.id();
        return service.save(meal, userId);
    }

    public void delete(int id)
    {
        int userId = AuthorizedUser.id();
        service.delete(id, userId);
    }

    public Meal get(int id)
    {
        int userId = AuthorizedUser.id();
        return service.get(id, userId);
    }

    public List<MealWithExceed> getAll()
    {
        int userId = AuthorizedUser.id();
        return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    public void update(Meal meal)
    {
        int userId = AuthorizedUser.id();
        service.update(meal, userId);
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime)

    {
        int userId = AuthorizedUser.id();
        return MealsUtil.getFilteredWithExceeded(service.getBetween(startDate, endDate, userId), startTime, endTime, AuthorizedUser.getCaloriesPerDay());
    }

}

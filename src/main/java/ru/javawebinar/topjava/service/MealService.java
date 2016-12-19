package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(Meal meal, int userId);
    void delete(int id, int userId)  throws NotFoundException;
    Meal get(int id, int userId)  throws NotFoundException;
    Collection<Meal> getAll (int userId);
    void update(Meal meal, int userId) throws NotFoundException;
    Collection<Meal> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, int userId);
}

package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (repository.get(id, userId)==null) throw new NotFoundException("");
        repository.delete(id, userId);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        if (repository.get(id, userId)==null) throw new NotFoundException("");
        return repository.get(id, userId);
    }

    @Override
    public Collection<Meal> getAll(int userId) throws NotFoundException {
        if (repository.getAll(userId).size()==0) throw new NotFoundException("");
        return repository.getAll(userId);
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException {
        if (repository.get(meal.getId(), userId)==null) throw new NotFoundException("");
        repository.save(meal, userId);
    }

    @Override
    public Collection<Meal> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, int userId) throws NotFoundException {
        if (repository.getBetween(startDate, startTime, endDate, endTime, userId).size()==0) throw new NotFoundException("");
        return repository.getBetween(startDate, startTime, endDate, endTime, userId);
    }
}

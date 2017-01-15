package ru.javawebinar.topjava.repository.datajpa;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
    private static final Sort SORT_DATETIME = new Sort("datetime");

    @Autowired
    private CrudMealRepository crudRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(crudUserRepository.findOne(userId));
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {

        return crudRepository.delete(id, userId)!=0;
    }

    @Override
    public Meal get(int id, int userId) {

        return DataAccessUtils.singleResult(crudRepository.findOne(id, userId));
    }

    @Override
    public List<Meal> getAll(int userId) {

        return crudRepository.findAll(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {

        return crudRepository.findAllBetween(userId, startDate, endDate);
    }

    @Override
    public Meal getWithUser(int id, int userId) {
       Meal meal = crudRepository.findOne(id);
       meal.setUser(crudUserRepository.findOne(userId));
       return meal;
    }
}

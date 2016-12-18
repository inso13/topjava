package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.USER_ID;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private Comparator<Meal> comparator = Comparator.comparing(Meal::getDateTime);

    {
       for (Meal meal: MealsUtil.MEALS)
           save(meal, USER_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
            meal.setUserId(userId);

        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id, int userId) {
        if (repository.get(id).getUserId()==userId)
        repository.remove(id);
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(id).getUserId()==userId)
        return repository.get(id);
        else return null;
    }

    @Override
    public List<Meal> getAll(int userId)
    {List<Meal> list = new ArrayList<>();

        for(Map.Entry<Integer, Meal> pair:repository.entrySet())
        {if (pair.getValue().getUserId()==userId)
        list.add(repository.get(pair.getKey()));
        list.sort(comparator);
        }
        if (list.size()==0) return Collections.emptyList();
        return list;
    }

    @Override
    public Collection<Meal> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, int userId) {

        Collection<Meal> result = new ArrayList<>();
        for(Map.Entry<Integer, Meal> pair:repository.entrySet()) {
        if ((pair.getValue().getUserId()==userId)
                && (DateTimeUtil.isBetweenTime(pair.getValue().getTime(), startTime, endTime))
            && (DateTimeUtil.isBetweenDate(pair.getValue().getDate(), startDate, endDate)))
        result.add(pair.getValue());
        }
        if (result.size()==0) return Collections.emptyList();
        return result;

    }
}


package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Котик on 12.12.2016.
 */
public class MealsDaoClass implements MealsDao
{
    private Map<Integer, Meal> dao = new ConcurrentHashMap<>();
    public static AtomicInteger count = new AtomicInteger(0);

    public MealsDaoClass()
    {
       dao.put(count.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, count.intValue()));
        dao.put(count.incrementAndGet(),new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, count.intValue()));
        dao.put(count.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, count.intValue()));
        dao.put(count.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, count.intValue()));
        dao.put(count.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, count.intValue()));
        dao.put(count.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, count.intValue()));
    }

    @Override
    public void create(Meal meal)
    {
        {
            dao.put(meal.getId(), meal);}
    }

    @Override
    public void remove(int id) {
        dao.remove(id);
    }

    @Override
    public Meal read(int id) {
        return dao.get(id);
    }

    @Override
    public List<Meal> getAll() {
        CopyOnWriteArrayList<Meal> list = new CopyOnWriteArrayList<>();
        list.addAll(dao.values());
        return list;
    }

    @Override
    public void update(Meal meal) {
        if (dao.containsKey(meal.getId())) dao.remove(meal.getId());
        create(meal);
    }
}

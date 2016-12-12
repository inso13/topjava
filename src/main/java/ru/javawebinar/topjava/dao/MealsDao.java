package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Котик on 12.12.2016.
 */
public interface MealsDao
{
    public void create(Meal meal);
    public void remove(int id);
    public Meal read(int id);
    public void update(Meal meal);
    public List<Meal> getAll();
}

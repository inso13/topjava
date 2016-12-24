package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * Created by Inso on 24.12.2016.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL2.getId(), START_SEQ);
        MATCHER.assertEquals(meal, MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(MEAL1.getId(), START_SEQ+1);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL3.getId(), START_SEQ);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL2, MEAL1), service.getAll(START_SEQ));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(MEAL3.getId(), START_SEQ+1);
    }


    @Test
    public void getBetweenDateTimes() throws Exception {
        Collection<Meal> all = service.getBetweenDateTimes(START_DATE_TIME, END_DATE_TIME, START_SEQ);
        MATCHER.assertCollectionEquals(all, Arrays.asList(MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void getBetweenDates() throws Exception {
        Collection<Meal> all = service.getBetweenDates(START_DATE_TIME.toLocalDate(), END_DATE_TIME.toLocalDate(), START_SEQ);
        MATCHER.assertCollectionEquals(all, Arrays.asList(MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void getAll() throws Exception {
        Collection<Meal> all = service.getAll(START_SEQ);
        MATCHER.assertCollectionEquals(all, Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("testDescription");
        updated.setCalories(350);
        service.update(updated, START_SEQ);
        MATCHER.assertEquals(updated, service.get(MEAL1.getId(), START_SEQ));
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFond() throws Exception {
        Meal updated = new Meal(MEAL4);
        updated.setDescription("testDescription2");
        updated.setCalories(360);
        service.update(updated, START_SEQ+1);

    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 500);
        Meal created = service.save(newMeal, START_SEQ);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1, newMeal), service.getAll(START_SEQ));
    }

}
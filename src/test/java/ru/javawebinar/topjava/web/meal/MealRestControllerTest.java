package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


/**
 * Created by Inso on 27.01.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        AuthorizedUser.setId(100001);
        mockMvc.perform(delete(REST_URL + ADMIN_MEAL1.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN_MEAL2), mealService.getAll(ADMIN_ID));
        AuthorizedUser.setId(100000);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEEDED.contentListMatcher(MealsUtil.getWithExceeded(MEALS, USER.getCaloriesPerDay())));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL2);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(750);
        mockMvc.perform(put(REST_URL + MEAL2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        MATCHER.assertEquals(updated, mealService.get(MEAL2.getId(), USER_ID));
    }

    @Test
    public void testCreate() throws Exception {
       AuthorizedUser.setId(100001);
        Meal created = new Meal(null, LocalDateTime.now(), "New Admin meal", 250);

        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))).andExpect(status().isCreated());

        Meal returned = MATCHER.fromJsonAction(action);
        created.setId(returned.getId());

        MATCHER.assertEquals(created, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(created, ADMIN_MEAL2, ADMIN_MEAL1), mealService.getAll(ADMIN_ID));
        AuthorizedUser.setId(100000);
    }

    @Test
    public void getBetween() throws Exception {
        String startDateTime = "2015-05-29T08:00:00";
        String endDateTime = "2015-05-30T17:00:00";

        TestUtil.print(mockMvc.perform(get(REST_URL+"filter?startDateTime="
                +startDateTime+"&endDateTime="
                +endDateTime)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEEDED.contentListMatcher(MealsUtil.getWithExceeded(Arrays.asList(MEAL2, MEAL1),
                        USER.getCaloriesPerDay())));
    }

    @Test
    public void between() throws Exception {
        String startDate = "2015-05-29";
        String endDate = "2015-05-30";
        String startTime= "08:00";
        String endTime="17:00";

        TestUtil.print(mockMvc.perform(get(REST_URL+"between?startDate="
               +startDate
                +"&endDate="+endDate)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEEDED.contentListMatcher(MealsUtil.getWithExceeded(Arrays.asList(MEAL3, MEAL2, MEAL1),
                        USER.getCaloriesPerDay())));
    }
}
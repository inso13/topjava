package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;


/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;
    private ConfigurableApplicationContext applicationContext;
    private ProfileRestController profileRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = applicationContext.getBean(MealRestController.class);
        profileRestController = applicationContext.getBean(ProfileRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action==null)
        {
            request.setCharacterEncoding("UTF-8");
            String id = request.getParameter("id");

            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            mealRestController.save(meal);
            response.sendRedirect("meals");

        }
        else {
            LocalDate startDate = DateTimeUtil.parseStartDate(request.getParameter("startDate"));
            LocalDate endDate = DateTimeUtil.parseEndDate(request.getParameter("endDate"));
            LocalTime startTime = DateTimeUtil.parseStartTime(request.getParameter("startTime"));
            LocalTime endTime = DateTimeUtil.parseEndTime(request.getParameter("endTime"));
            request.setAttribute("meals", mealRestController.getBetween(startDate, startTime, endDate, endTime));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        request.setAttribute("userName", profileRestController.get(AuthorizedUser.id()).getName()); //что бы написать чья еда

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("meals",
                    mealRestController.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealRestController.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                    mealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    public void destroy() {
        super.destroy();
        applicationContext.close();
    }
}
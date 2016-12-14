package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsDaoClass;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Inso on 10.12.2016.
 */
public class MealServlet extends HttpServlet {
    private MealsDaoClass daoClass;
    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        daoClass = new MealsDaoClass();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOG.debug("redirect to meals");

        String action = req.getParameter("action");
        if (action == null) {
            LOG.info("getAll");
            List<MealWithExceed> mealWithExceedList = MealsUtil.getFilteredWithExceeded(daoClass.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);

            req.setAttribute("mealWithExceedList", mealWithExceedList);

            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        } else if (action.equalsIgnoreCase("remove")) {
            int id = Integer.parseInt(req.getParameter("id"));
            daoClass.remove(id);
            LOG.info("Remove{}", id);
            List<Meal> list = daoClass.getAll();
            req.setAttribute("mealWithExceedList", MealsUtil.getFilteredWithExceeded
                    (list, LocalTime.MIN, LocalTime.MAX, 2000));
            resp.sendRedirect("meals");
        } else if (action.equalsIgnoreCase("update")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Meal meal = daoClass.read(id);
            LOG.info("Update{}", id);
            daoClass.remove(id);
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("mealsEdit.jsp").forward(req, resp);


        } else if (action.equalsIgnoreCase("create")) {
            Meal meal = new Meal(LocalDateTime.now(), "Новая еда", 500, MealsDaoClass.count.incrementAndGet());
            LOG.info("Create{}", meal.getId());
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("mealsEdit.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String date = req.getParameter("datetime");
        String desc = req.getParameter("description");
        String cal = req.getParameter("calories");
        int id;
        if (req.getParameter("id") == null) id = MealsDaoClass.count.incrementAndGet();
        else id = Integer.valueOf(req.getParameter("id"));


        Meal meal = new Meal(LocalDateTime.parse(date),
                desc,
                Integer.valueOf(cal),
                id);

        daoClass.create(meal);
        resp.sendRedirect("meals");
    }
}

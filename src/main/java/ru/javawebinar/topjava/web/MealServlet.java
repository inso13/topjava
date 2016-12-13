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
public class MealServlet extends HttpServlet
{
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
        if (action==null) {List<MealWithExceed> mealWithExceedList = MealsUtil.getFilteredWithExceeded(daoClass.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);

            req.setAttribute("mealWithExceedList", mealWithExceedList);

            req.getRequestDispatcher("/meals.jsp").forward(req, resp);}

        else if (action.equalsIgnoreCase("remove")){
            int id = Integer.parseInt(req.getParameter("id"));
            daoClass.remove(id);
            List<Meal> list = daoClass.getAll();
            req.setAttribute("mealWithExceedList", MealsUtil.getFilteredWithExceeded
                    (list, LocalTime.MIN, LocalTime.MAX, 2000));
            resp.sendRedirect("meals");
        }
        else if(action.equalsIgnoreCase("update"))
        {
            int id = Integer.parseInt(req.getParameter("id"));
            daoClass.remove(id);

            Meal meal = new Meal(LocalDateTime.now(), "", 500, id);

            req.setAttribute("meal", meal);
            req.getRequestDispatcher("mealsEdit.jsp").forward(req, resp);


        }

        else if(action.equalsIgnoreCase("create"))
        {


        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
       Meal meal = new Meal(LocalDateTime.now(), req.getParameter("description"), 500, MealsDaoClass.count.incrementAndGet());
        //meal.setDescription(req.getParameter("Description"));
        daoClass.create(meal);
        resp.sendRedirect("meals");
    }
}

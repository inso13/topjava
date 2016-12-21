package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class UserServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);
    private ConfigurableApplicationContext applicationContext;
    private ProfileRestController profileRestController;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to users");

        request.setAttribute("users", profileRestController.getAll());
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        profileRestController = applicationContext.getBean(ProfileRestController.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        applicationContext.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int userId = Integer.valueOf(req.getParameter("userId"));
        AuthorizedUser.setUserId(userId);
        resp.sendRedirect("meals");
    }
}

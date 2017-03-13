package com.qulix.zakrevskynp.trainingtask.web.task.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.TaskDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;

@WebServlet("/addTaskProject")
public class AddTaskProjectServlet extends CustomServlet {

    private Logger logger = Logger.getLogger(AddTaskProjectServlet.class.getName());
    private List<String> errors = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("isDisable", true);
            request.setAttribute("personsList", new PersonDAOImpl().getPersonsList());
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        } catch (CustomException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("tasksList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        TasksDAO tasksDAO = new TasksDAOImpl();
        Map<String, Object> parameters = getParametersFromRequest(request);
        errors = new TaskDataValidator().validate(parameters);
        try {
            if (errors.size() == 0) {
                List<Map<String, Object>> tasks = tasksDAO.addTask(parameters, session);
                session.setAttribute("tasks", tasks);
                request.setAttribute("action", "addProject");
                request.setAttribute("tasks", tasks);
                response.sendRedirect("addProject");
            }
            else {
                request.setAttribute("personsList",  new PersonDAOImpl().getPersonsList());
                request.setAttribute("action", "addTaskProject");
                request.setAttribute("errors", errors);
                request.setAttribute("task", parameters);
                request.getRequestDispatcher("taskView.jsp").forward(request, response);
            }
        } catch (CustomException e) {
            logger.log(Level.SEVERE, e.getMessage());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("tasksList.jsp").forward(request, response);
        }
    }
}

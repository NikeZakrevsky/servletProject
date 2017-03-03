package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.dao.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.validator.TaskDataValidator;

/**
 * Show view with form for adding new task and handling it data
 * @author Q-NZA
 */
@WebServlet("/addTask")
public class AddTaskServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(AddTaskServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        TasksDAO taskDAO = new TasksDAOImpl();
        List<String> parametersNames = Collections.list(request.getParameterNames());
        Map<String, Object> parameters = parametersNames.stream().collect(Collectors.toMap(x -> x, request::getParameter));

        errors = new TaskDataValidator().validate(parameters);
        try {
            if (errors.size() == 0) {
                taskDAO.addTask(parameters);
                response.sendRedirect("tasksList");
            }
            else {
                request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
                request.setAttribute("personsList",  new PersonDAOImpl().getPersonsList());
                request.setAttribute("action", "addTask");
                request.setAttribute("errors", errors);
                request.setAttribute("task", parameters);
                request.getRequestDispatcher("taskView.jsp").forward(request, response);
            }
            } catch (DAOException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("tasksList.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
            request.setAttribute("personsList",  new PersonDAOImpl().getPersonsList());
            request.setAttribute("action", "addTask");
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        } catch (DAOException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("tasksList.jsp").forward(request, response);
        }
    }
}

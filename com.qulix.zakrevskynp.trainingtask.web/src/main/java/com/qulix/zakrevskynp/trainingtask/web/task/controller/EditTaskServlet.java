package com.qulix.zakrevskynp.trainingtask.web.task.controller;

import java.io.IOException;
import java.sql.SQLException;
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

import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.Task;
import com.qulix.zakrevskynp.trainingtask.web.task.TaskDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;

/**
 * Show view with form for editing new person and handling it data
 * @author Q-NZA
 */
@WebServlet("/editTask")
public class EditTaskServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(EditTaskServlet.class.getName());
    private String returningPath = "tasksList";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        TaskDataValidator validator = new TaskDataValidator();
        List<String> parametersNames = Collections.list(request.getParameterNames());
        Map<String, Object> parameters = parametersNames.stream().collect(Collectors.toMap(x -> x, request::getParameter));

        errors = validator.validate(parameters);
        try {
            if (errors.size() == 0) {
                TasksDAO tasksDAO = new TasksDAOImpl();
                tasksDAO.updateTask(parameters);
                response.sendRedirect(returningPath);
            } else {
                request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
                request.setAttribute("personsList",  new PersonDAOImpl().getPersonsList());
                request.setAttribute("action", "editTask");
                request.setAttribute("errors", errors);
                request.setAttribute("task", parameters);
                request.getRequestDispatcher("taskView.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("tasksList.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        returningPath = request.getSession(false).getAttribute("path").toString();

        try {
            TasksDAO taskDAO = new TasksDAOImpl();

            Task task = taskDAO.getTaskById(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("task", task);
            request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
            request.setAttribute("personsList",  new PersonDAOImpl().getPersonsList());
            request.setAttribute("action", "editTask");
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        } catch (CustomException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("tasksList.jsp").forward(request, response);
        }
    }
}

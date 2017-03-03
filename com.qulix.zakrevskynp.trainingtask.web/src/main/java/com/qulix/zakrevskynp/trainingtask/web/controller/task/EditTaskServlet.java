package com.qulix.zakrevskynp.trainingtask.web.controller.task;

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

import com.qulix.zakrevskynp.trainingtask.web.dao.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;
import com.qulix.zakrevskynp.trainingtask.web.validator.TaskDataValidator;

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

        if (errors.size() == 0) {
            TasksDAO tasksDAO = new TasksDAOImpl();
            try {
                tasksDAO.updateTask(parameters);
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
                errors.clear();
                errors.add(e.getMessage());
                request.setAttribute("error", errors);
                request.getRequestDispatcher("tasksList.jsp").forward(request, response);
            }
            response.sendRedirect(returningPath);
        } else {
            response.sendRedirect("editTask?id=" + request.getParameter("id"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getSession(false).getAttribute("path") != null) {
            returningPath = request.getSession(false).getAttribute("path").toString();
            request.getSession().invalidate();
        }
        else {
            String referrer = request.getHeader("referer");
            String[] splited = referrer.split("/");
            returningPath = splited[splited.length - 1];
        }
        System.out.println(returningPath);
        try {
            TasksDAO taskDAO = new TasksDAOImpl();
            PersonDAO personDAO = new PersonDAOImpl();
            ProjectDAO projectDAO = new ProjectDAOImpl();

            Task task = taskDAO.getTaskById(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("task", task);
            request.setAttribute("projectsList", projectDAO.getProjectsList());
            request.setAttribute("personsList", personDAO.getPersonsList());
            request.setAttribute("projectShortName", task.getProjectShortName());
            request.setAttribute("errors", errors);
            request.setAttribute("action", "editTask");
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

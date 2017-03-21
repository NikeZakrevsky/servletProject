package com.qulix.zakrevskynp.trainingtask.web.task.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.TaskDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;

/**
 * Show edit task form and handling form data for editing task in session
 * @author Q-NZA
 */
@WebServlet("/editTaskProject")
public class EditTaskProjectServlet extends CustomServlet {

    private List<String> errors = new ArrayList<>();
    private String returningPath;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TaskDataValidator validator = new TaskDataValidator();
        Map<String, Object> parameters = getParametersFromRequest(request);

        returningPath = request.getSession().getAttribute("path").toString();
        errors = validator.validate(parameters);
        if (errors.size() == 0) {
            TasksDAOImpl tasksDAO = new TasksDAOImpl();
            tasksDAO.updateTask(parameters, request.getSession(), Integer.parseInt(request.getParameter("id")));
            response.sendRedirect(returningPath);
        }
        else {
            request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
            request.setAttribute("personsList", new PersonDAOImpl().getPersonsList());
            request.setAttribute("action", "editTaskProject");
            request.setAttribute("errors", errors);
            request.setAttribute("task", parameters);
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        }
    }
}
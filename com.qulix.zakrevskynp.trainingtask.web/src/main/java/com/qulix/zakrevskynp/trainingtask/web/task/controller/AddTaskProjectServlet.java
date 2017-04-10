package com.qulix.zakrevskynp.trainingtask.web.task.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.Task;
import com.qulix.zakrevskynp.trainingtask.web.task.TaskDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;

/**
 * Show add task form and handling it data for adding task in session
 * @author Q-NZA
 */
@WebServlet("/taskProject")
public class AddTaskProjectServlet extends CustomTaskServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("isDisable", true);
        Map<String, Object> parameters = getParametersFromRequest(request);
        List<String> errors = new TaskDataValidator().validate(parameters);
        if (errors.size() == 0) {
            Task task = parametersToObject(parameters);
            List<Task> resultTasks = new TasksDAOImpl().addTask(task, request.getSession());
            request.getSession().setAttribute("resultTasks", resultTasks);
            response.sendRedirect(request.getSession().getAttribute("path").toString());
        }
        else {
            request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
            request.setAttribute("personsList",  new PersonDAOImpl().getPersonsList());
            request.setAttribute("action", "taskProject");
            request.setAttribute("errors", errors);
            request.setAttribute("task", parameters);
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        }
    }
}
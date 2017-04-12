package com.qulix.zakrevskynp.trainingtask.web.controller.task;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;

/**
 * Show add task form and handling it data for adding task in database
 * @author Q-NZA
 */
@WebServlet("/addTask")
public class AddTaskServlet extends CustomTaskServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> parameters = getParametersFromRequest(request);
        List<String> errors = new TaskDataValidator().validate(parameters);
        Task task = parametersToObject(parameters);
        if (errors.size() == 0) {
            new TasksDAOImpl().addTask(task);
            response.sendRedirect("tasksList");
        }
        else {
            request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
            request.setAttribute("personsList",  new PersonDAOImpl().getPersonsList());
            request.setAttribute("action", "addTask");
            request.setAttribute("errors", errors);
            request.setAttribute("task", task);
            request.getRequestDispatcher("view/taskView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
        request.setAttribute("personsList",  new PersonDAOImpl().getPersonsList());
        request.setAttribute("action", "addTask");
        request.setAttribute("path", "tasksList");
        request.getRequestDispatcher("view/taskView.jsp").forward(request, response);
    }
}

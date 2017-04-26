package com.qulix.zakrevskynp.trainingtask.web.controller.task;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.TaskDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Shows the form of adding a new task and processing its data
 *
 * @author Q-NZA
 */
@WebServlet("/addTask")
public class AddTaskServlet extends CustomTaskServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> parameters = getParametersFromRequest(request);
        List<String> errors = new TaskDataValidator().validate(parameters);
        if (errors.isEmpty()) {
            Task task = parametersToObject(parameters);
            new TaskDAOImpl().add(task);
            response.sendRedirect(Attribute.TASKS_LIST);
        }
        else {
            request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDAOImpl().getAll());
            request.setAttribute(Attribute.PERSONS_LIST_NAME,  new PersonDAOImpl().getAll());
            request.setAttribute(Attribute.ACTION, Attribute.ADD_TASK);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            request.setAttribute(Attribute.TASK_OBJECT_NAME, parameters);
            request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDAOImpl().getAll());
        request.setAttribute(Attribute.PERSONS_LIST_NAME,  new PersonDAOImpl().getAll());
        request.setAttribute(Attribute.ACTION, Attribute.ADD_TASK);
        request.setAttribute(Attribute.PATH, Attribute.TASKS_LIST);
        request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
    }
}

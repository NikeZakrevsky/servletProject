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
 * Show the form for adding new task in session
 * @author Q-NZA
 */
@WebServlet("/taskProject")
public class AddTaskProjectServlet extends CustomTaskServlet {

    private static final String IS_DISABLE = "isDisable";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(IS_DISABLE, true);
        Map<String, Object> parameters = getParametersFromRequest(request);
        List<String> errors = new TaskDataValidator().validate(parameters);
        if (errors.isEmpty()) {
            Task task = parametersToObject(parameters);
            List<Task> tasks = getItems(request.getSession().getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
            List<Task> resultTasks = new TaskDAOImpl().addTask(task, tasks);
            request.getSession().setAttribute(Attribute.RESULT_TASKS_LIST_NAME, resultTasks);
            response.sendRedirect(request.getSession().getAttribute(Attribute.PATH).toString());
        }
        else {
            request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDAOImpl().getAll());
            request.setAttribute(Attribute.PERSONS_LIST_NAME,  new PersonDAOImpl().getAll());
            request.setAttribute(Attribute.ACTION, Attribute.TASK_PROJECT);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            request.setAttribute(Attribute.TASK_OBJECT_NAME, parameters);
            request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
        }
    }
}
package com.qulix.zakrevskynp.trainingtask.web.controller.task;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Show add task form and handling it data for adding task in session
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
            List<Task> tasks = (List<Task>) request.getSession().getAttribute(Attribute.RESULT_TASKS_LIST_NAME);
            List<Task> resultTasks = new TasksDAOImpl(Task.class).addTask(task, tasks);
            request.getSession().setAttribute(Attribute.RESULT_TASKS_LIST_NAME, resultTasks);
            response.sendRedirect(request.getSession().getAttribute(Attribute.PATH).toString());
        }
        else {
            request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDAOImpl(Project.class).getProjectsList());
            request.setAttribute(Attribute.PERSONS_LIST_NAME,  new PersonDAOImpl(Person.class).getPersonsList());
            request.setAttribute(Attribute.ACTION, Attribute.TASK_PROJECT);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            request.setAttribute(Attribute.TASK_OBJECT_NAME, parameters);
            request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
        }
    }
}
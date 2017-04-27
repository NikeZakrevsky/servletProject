package com.qulix.zakrevskynp.trainingtask.web.controller.task;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDaoImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.ProjectDaoImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.TaskDaoImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Show the form of updating a task in session
 *
 * @author Q-NZA
 */
@WebServlet("/editTaskProject")
public class EditTaskProjectServlet extends CustomTaskServlet {

    private static final String ID = "id";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskDataValidator validator = new TaskDataValidator();
        Map<String, Object> parameters = getParametersFromRequest(request);
        String returningPath = request.getSession().getAttribute(Attribute.PATH).toString();
        List<String> errors = validator.validate(parameters);
        if (errors.isEmpty()) {
            Task task = parametersToObject(parameters);
            TaskDaoImpl tasksDAO = new TaskDaoImpl();
            List<Task> tasks = getItems(request.getSession().getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
            List<Task> resultTasks = tasksDAO.updateTaskInList(task, tasks, Integer.parseInt(request.getParameter(ID)));
            request.getSession().setAttribute(Attribute.RESULT_TASKS_LIST_NAME, resultTasks);
            response.sendRedirect(returningPath);
        }
        else {
            request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDaoImpl().getAll());
            request.setAttribute(Attribute.PERSONS_LIST_NAME, new PersonDaoImpl().getAll());
            request.setAttribute(Attribute.ACTION, Attribute.EDIT_TASK_PROJECT);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            request.setAttribute(Attribute.TASK_OBJECT_NAME, parameters);
            request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
        }
    }
}
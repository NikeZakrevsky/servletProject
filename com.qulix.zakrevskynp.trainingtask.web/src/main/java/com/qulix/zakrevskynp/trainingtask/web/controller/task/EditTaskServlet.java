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
 * Shows the form of updating a task and processing its data
 *
 * @author Q-NZA
 */
@WebServlet("/editTask")
public class EditTaskServlet extends CustomTaskServlet {

    private String returningPath;
    private static final String ID = "id";
    private static final String IS_DISABLE = "isDisable";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskDataValidator validator = new TaskDataValidator();
        Map<String, Object> parameters = getParametersFromRequest(request);
        List<String> errors = validator.validate(parameters);
        if (errors.isEmpty()) {
            Task task = parametersToObject(parameters);
            new TaskDaoImpl().update(task);
            response.sendRedirect(returningPath);
        }
        else {
            request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDaoImpl().getAll());
            request.setAttribute(Attribute.PERSONS_LIST_NAME,  new PersonDaoImpl().getAll());
            request.setAttribute(Attribute.ACTION, Attribute.EDIT_TASK);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            request.setAttribute(Attribute.TASK_OBJECT_NAME, parameters);
            request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(Attribute.PATH, request.getSession().getAttribute(Attribute.PATH).toString());
        returningPath = request.getSession(false).getAttribute(Attribute.PATH).toString();
        Task task = new TaskDaoImpl().get(Integer.parseInt(request.getParameter(ID)));
        request.setAttribute(Attribute.TASK_OBJECT_NAME, task);
        request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDaoImpl().getAll());
        request.setAttribute(Attribute.PERSONS_LIST_NAME,  new PersonDaoImpl().getAll());
        request.setAttribute(Attribute.ACTION, Attribute.EDIT_TASK);
        if (!returningPath.equals(Attribute.TASKS_LIST)) {
            request.setAttribute(IS_DISABLE, true);
        }
        request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
    }
}

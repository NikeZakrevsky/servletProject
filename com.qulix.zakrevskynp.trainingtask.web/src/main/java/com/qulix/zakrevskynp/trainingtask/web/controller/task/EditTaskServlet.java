package com.qulix.zakrevskynp.trainingtask.web.controller.task;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDao;
import com.qulix.zakrevskynp.trainingtask.web.dao.ProjectDao;
import com.qulix.zakrevskynp.trainingtask.web.dao.TaskDao;
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
        Map<String, Object> parameters = getParametersFromRequest(request);

        TaskDataValidator validator = new TaskDataValidator();
        List<String> errors = validator.validate(parameters);
        
        if (errors.isEmpty()) {
            Task task = parametersToObject(parameters);
            new TaskDao().update(task);
            response.sendRedirect(returningPath);
        }
        else {
            request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDao().getAll());
            request.setAttribute(Attribute.PERSONS_LIST_NAME,  new PersonDao().getAll());
            request.setAttribute(Attribute.ACTION, Attribute.EDIT_TASK);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            request.setAttribute(Attribute.TASK_OBJECT_NAME, parameters);
            request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(Attribute.PATH, request.getSession().getAttribute(Attribute.PATH).toString());
        returningPath = request.getSession(false).getAttribute(Attribute.PATH).toString();
        Task task = new TaskDao().get(Integer.parseInt(request.getParameter(ID)));
        request.setAttribute(Attribute.TASK_OBJECT_NAME, task);
        request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDao().getAll());
        request.setAttribute(Attribute.PERSONS_LIST_NAME,  new PersonDao().getAll());
        request.setAttribute(Attribute.ACTION, Attribute.EDIT_TASK);
        if (!returningPath.equals(Attribute.TASKS_LIST)) {
            request.setAttribute(IS_DISABLE, true);
        }
        request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
    }
}

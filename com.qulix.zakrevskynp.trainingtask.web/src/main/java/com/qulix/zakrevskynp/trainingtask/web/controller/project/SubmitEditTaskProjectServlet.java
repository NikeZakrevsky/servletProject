package com.qulix.zakrevskynp.trainingtask.web.controller.project;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Save project form data, while edit tasks
 * @author Q-NZA
 */
@WebServlet("/editTaskProject1")
public class SubmitEditTaskProjectServlet extends CustomProjectServlet {
    private static final String IS_DISABLE = "isDisable";
    private static final String TASK_ID = "taskId";
    private static final String EDIT_TASK_PROJECT = "editTaskProject";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(Attribute.PROJECT_OBJECT_NAME, getParametersFromRequest(request));
        request.setAttribute(Attribute.PATH, request.getSession().getAttribute(Attribute.PATH).toString());
        List<Task> tasks = getItems(request.getSession().getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
        for (Task task : tasks) {
            if (task.getId() == Integer.parseInt(request.getParameter(TASK_ID))) {
                request.setAttribute(Attribute.TASK_OBJECT_NAME, task);
                request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDAOImpl().getProjectsList());
                request.setAttribute(Attribute.PERSONS_LIST_NAME, new PersonDAOImpl().getPersonsList());
                request.setAttribute(IS_DISABLE, true);
                request.setAttribute(Attribute.ACTION, EDIT_TASK_PROJECT);
                request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
            }
        }
    }
}

package com.qulix.zakrevskynp.trainingtask.web.controller.project;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Shows the form for adding new project and processes its data
 * @author Q-NZA
 */
@WebServlet("/addProject")
public class AddProjectServlet extends CustomProjectServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> parameters = getParametersFromRequest(request);

        List<String> errors = new ProjectDataValidator().validate(parameters);
        if (errors.isEmpty()) {
            Project project = parametersToObject(parameters);
            List<Task> tasks = getItems(request.getSession().getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
            new ProjectDAOImpl().addProject(project, tasks);
            request.getSession().invalidate();
            response.sendRedirect(Attribute.REDIRECT_PROJECT_LIST);
        }
        else {
            HttpSession session = request.getSession();
            request.setAttribute(Attribute.PROJECT_OBJECT_NAME, parameters);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            List<Task> tasks = getItems(session.getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
            request.setAttribute(Attribute.TASKS_LIST_NAME, tasks);
            request.getRequestDispatcher(Attribute.PROJECT_VIEW).forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(Attribute.ACTION, Attribute.ADD_PROJECT);
        request.getSession(true).setAttribute(Attribute.PATH,  Attribute.ADD_PROJECT);
        request.setAttribute(Attribute.PATH, Attribute.ADD_PROJECT);
        HttpSession session = request.getSession();
        List<Task> tasks = getItems(session.getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
        session.setAttribute(Attribute.RESULT_TASKS_LIST_NAME, tasks);
        request.setAttribute(Attribute.TASKS_LIST_NAME, tasks);
        request.getRequestDispatcher(Attribute.PROJECT_VIEW).forward(request, response);
    }
}

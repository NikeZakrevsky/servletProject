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
import com.qulix.zakrevskynp.trainingtask.web.dao.ProjectDao;
import com.qulix.zakrevskynp.trainingtask.web.dao.TaskDao;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Shows the form of updating a project and processes its data
 *
 * @author Q-NZA
 */
@WebServlet("/editProject")
public class EditProjectServlet extends CustomProjectServlet {

    private static final String ID = "id";
    private static final String EDIT_PROJECT = "editProject?id=";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> parameters = getParametersFromRequest(request);

        ProjectDataValidator validator = new ProjectDataValidator();
        List<String> errors = validator.validate(parameters);
        
        if (errors.isEmpty()) {
            Project project = parametersToObject(parameters);
            Project project1 = new ProjectDao().get(Integer.parseInt(request.getParameter(ID)));
            project.setTasks(project1.getTasks());
            new ProjectDao().update(project);
            new TaskDao().updateChangedTasks(request, project);

            response.sendRedirect(Attribute.REDIRECT_PROJECT_LIST);
        }
        else {
            request.setAttribute(Attribute.PROJECT_OBJECT_NAME, parameters);
            HttpSession session = request.getSession();
            List<Task> resultTasks = getItems(session.getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
            request.setAttribute(Attribute.TASKS_LIST_NAME, resultTasks);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);

            request.getRequestDispatcher(Attribute.PROJECT_VIEW).forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Project project;
        if (request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME) == null) {
            ProjectDao projectDao = new ProjectDao();
            project = projectDao.get(Integer.parseInt(request.getParameter(ID)));
            request.getSession().setAttribute(Attribute.PROJECT_OBJECT_NAME, project);
        }
        else {
            project = (Project) request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME);
        }
        request.setAttribute(Attribute.PROJECT_OBJECT_NAME, project);
        request.getSession().setAttribute(Attribute.PATH, EDIT_PROJECT + request.getParameter(ID));
        request.setAttribute(Attribute.PATH, EDIT_PROJECT + request.getParameter(ID));

        request.getRequestDispatcher(Attribute.PROJECT_VIEW).forward(request, response);
    }

}

package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;

/**
 * Save project form data, while add tasks
 * @author Q-NZA
 */
@WebServlet("/taskProject1")
public class SubmitAddTaskProjectServlet extends CustomProjectServlet {
    private static final String ID = "id";
    private static final String IS_DISABLE = "isDisable";
    private static final String PROJECT_ID = "projectId";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> parameters = getParametersFromRequest(request);
        request.getSession().setAttribute(Attribute.PROJECT_OBJECT_NAME, parameters);
        request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDAOImpl().getProjectsList());
        request.setAttribute(Attribute.PERSONS_LIST_NAME, new PersonDAOImpl().getPersonsList());
        if (!request.getParameter(ID).equals("")) {
            Map<String, Object> task = new HashMap<>();
            task.put(PROJECT_ID, Integer.parseInt(request.getParameter(ID)));
            request.setAttribute(Attribute.TASK_OBJECT_NAME, task);
        }
        request.setAttribute(IS_DISABLE, true);
        request.setAttribute(Attribute.ACTION, Attribute.TASK_PROJECT);
        request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
    }
}

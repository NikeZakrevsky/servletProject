package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;

/**
 * Save project form data, while add tasks
 * @author Q-NZA
 */
@WebServlet("/taskProject1")
public class SubmitAddTaskProjectServlet extends CustomServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> parameters = getParametersFromRequest(request);
        request.getSession().setAttribute("project", parameters);
        request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
        request.setAttribute("personsList", new PersonDAOImpl().getPersonsList());
        if (!request.getParameter("id").equals("")) {
            Map<String, Object> task = new HashMap<>();
            task.put("projectId", Integer.parseInt(request.getParameter("id")));
            request.setAttribute("task", task);
        }
        request.setAttribute("isDisable", true);
        request.setAttribute("action", "taskProject");
        request.getRequestDispatcher("view/taskView.jsp").forward(request, response);
    }
}

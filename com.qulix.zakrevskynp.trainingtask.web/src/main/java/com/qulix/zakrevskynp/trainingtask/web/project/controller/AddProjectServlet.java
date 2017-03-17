package com.qulix.zakrevskynp.trainingtask.web.project.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.project.ProjectDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;

/**
 * Show view with form for adding new project and handling it data
 * @author Q-NZA
 */
@WebServlet("/addProject")
public class AddProjectServlet extends CustomServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> parameters = getParametersFromRequest(request);

        List<String> errors = new ProjectDataValidator().validate(parameters);
        if (errors.size() == 0) {
            List<Map<String, Object>> tasks = (List<Map<String, Object>>)request.getSession().getAttribute("resultTasks");
            new ProjectDAOImpl().addProject(parameters, tasks);
            request.getSession().invalidate();
            response.sendRedirect("projectsList");
        }
        else {
            HttpSession session = request.getSession();
            request.setAttribute("project", parameters);
            request.setAttribute("errors", errors);
            List<Map<String, Object>> tasks = (List<Map<String, Object>>)session.getAttribute("resultTasks");
            request.setAttribute("tasks", tasks);
            request.getRequestDispatcher("projectView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", "addProject");
        request.getSession(true).setAttribute("path", "addProject");
        request.setAttribute("path", "addProject");
        HttpSession session = request.getSession();
        List<Map<String, Object>> tasks = (List<Map<String, Object>>)session.getAttribute("resultTasks");
        session.setAttribute("resultTask", tasks);
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("projectView.jsp").forward(request, response);
    }
}

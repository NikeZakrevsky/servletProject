package com.qulix.zakrevskynp.trainingtask.web.project.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.person.controller.AddPersonServlet;
import com.qulix.zakrevskynp.trainingtask.web.project.ProjectDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;

/**
 * Show view with form for adding new project and handling it data
 * @author Q-NZA
 */
@WebServlet("/addProject")
public class AddProjectServlet extends HttpServlet {

    private ProjectDAO projectDAO = new ProjectDAOImpl();
    private Logger logger = Logger.getLogger(AddPersonServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        List<String> parametersNames = Collections.list(request.getParameterNames());
        Map<String, Object> parameters = parametersNames.stream().collect(Collectors.toMap(x -> x, request::getParameter));

        List<String> errors = new ProjectDataValidator().validate(parameters);
        if (errors.size() == 0) {
            try {
                List<Map<String, Object>> tasks = (List<Map<String, Object>>)request.getSession().getAttribute("resultTasks");
                projectDAO.addProject(parameters, tasks);
                request.getSession().invalidate();
            } catch (CustomException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
                errors.clear();
                errors.add(e.getMessage());
                request.setAttribute("error", errors);
                request.getRequestDispatcher("projectsList.jsp").forward(request, response);
            }
            response.sendRedirect("projectsList");
        }
        else {
            request.setAttribute("project", parameters);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("projectView.jsp").forward(request, response);
        }
    }

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

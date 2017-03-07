package com.qulix.zakrevskynp.trainingtask.web.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

import com.qulix.zakrevskynp.trainingtask.web.util.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.project.Project;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.project.ProjectDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.Task;

/**
 * Show view with form for editing new project and handling it data
 * @author Q-NZA
 */
@WebServlet("/editProject")
public class EditProjectServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(EditProjectServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ProjectDAO dao = new ProjectDAOImpl();
        ProjectDataValidator validator = new ProjectDataValidator();

        List<String> parametersNames = Collections.list(request.getParameterNames());
        Map<String, Object> parameters = parametersNames.stream().collect(Collectors.toMap(x -> x, request::getParameter));

        errors = validator.validate(parameters);
        try {
            if (errors.size() == 0) {
                dao.updateProject(parameters);
                response.sendRedirect("projectsList");
            }
            else {
                request.setAttribute("project", parameters);
                List<Task> tasks = new TasksDAOImpl().getTasksByProjectId(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("tasks", tasks);
                request.setAttribute("errors", errors);
                request.getSession(true).setAttribute("path", "editProject?id=" + request.getParameter("id"));
                request.getRequestDispatcher("projectView.jsp").forward(request, response);
            }
        } catch (CustomException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("projectList.jsp").forward(request, response);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectDAO dao = new ProjectDAOImpl();
        TasksDAO tasksDAO = new TasksDAOImpl();
        try {
            Project project = dao.getProjectById(Integer.parseInt(request.getParameter("id")));
            List<Task> tasks = tasksDAO.getTasksByProjectId(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("tasks", tasks);
            request.setAttribute("project", project);
            request.setAttribute("action", "editProject");
            request.getRequestDispatcher("projectView.jsp").forward(request, response);
        } catch (CustomException e) {
            errors.clear();
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("projectList.jsp").forward(request, response);
        }

    }
}

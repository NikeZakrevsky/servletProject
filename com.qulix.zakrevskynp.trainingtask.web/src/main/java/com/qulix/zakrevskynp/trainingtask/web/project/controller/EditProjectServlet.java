package com.qulix.zakrevskynp.trainingtask.web.project.controller;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.project.Project;
import com.qulix.zakrevskynp.trainingtask.web.project.ProjectDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.Task;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;

/**
 * Show view with form for editing new project and handling it data
 * @author Q-NZA
 */
@WebServlet("/editProject")
public class EditProjectServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(EditProjectServlet.class.getName());
    private ProjectDAO dao = new ProjectDAOImpl();
    private List<Map<String, Object>> resultTasks;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ProjectDataValidator validator = new ProjectDataValidator();

        List<String> parametersNames = Collections.list(request.getParameterNames());
        Map<String, Object> parameters = parametersNames.stream().collect(Collectors.toMap(x -> x, request::getParameter));

        errors = validator.validate(parameters);
        try {
            if (errors.size() == 0) {
                dao.updateProject(parameters);
                List<Map<String, Object>> resultTasks = (List<Map<String, Object>>)request.getSession().getAttribute("resultTasks");
                TasksDAOImpl tasksDAO = new TasksDAOImpl();
                for (Map<String, Object> task: tasksDAO.getTasksByProjectId(Integer.parseInt(request.getParameter("id")))) {
                    tasksDAO.removeTask(Integer.parseInt(task.get("id").toString()));
                }
                for (Map<String, Object> task : resultTasks) {
                    tasksDAO.addTask(task);
                }
                response.sendRedirect("projectsList");
            }
            else {
                request.setAttribute("project", parameters);
                List<Map<String, Object>> resultTasks = (List<Map<String, Object>>)request.getSession().getAttribute("resultTasks");
                request.setAttribute("tasks", resultTasks);
                request.setAttribute("errors", errors);
                request.setAttribute("action", "editProject");
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
        try {

            if (request.getSession().getAttribute("resultTasks") == null) {
                resultTasks = new ArrayList<>();
                List<Map<String, Object>> tasks = new TasksDAOImpl().getTasksByProjectId(Integer.parseInt(request.getParameter("id")));
                if (tasks != null) {
                    tasks.forEach(resultTasks::add);
                }
                request.getSession().setAttribute("resultTasks", resultTasks);
            }
            else
                resultTasks = (List<Map<String, Object>>)request.getSession().getAttribute("resultTasks");

            Project project = dao.getProjectById(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("tasks", resultTasks);
            request.setAttribute("project", project);
            request.setAttribute("action", "editProject");

            request.getSession(true).setAttribute("path", "editProject?id=" + request.getParameter("id"));
            request.setAttribute("path", "editProject?id=" + request.getParameter("id"));
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

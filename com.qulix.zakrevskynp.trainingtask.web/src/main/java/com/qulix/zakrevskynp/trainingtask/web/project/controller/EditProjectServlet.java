package com.qulix.zakrevskynp.trainingtask.web.project.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.project.Project;
import com.qulix.zakrevskynp.trainingtask.web.project.ProjectDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;

/**
 * Show view with form for editing new project and handling it data
 * @author Q-NZA
 */
@WebServlet("/editProject")
public class EditProjectServlet extends CustomServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(EditProjectServlet.class.getName());
    private ProjectDAO dao = new ProjectDAOImpl();
    private List<Map<String, Object>> resultTasks;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Map<String, Object> parameters = getParametersFromRequest(request);
        ProjectDataValidator validator = new ProjectDataValidator();
        errors = validator.validate(parameters);
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
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("resultTasks") == null) {
            resultTasks = new ArrayList<>();
            TasksDAOImpl tasksDAO = new TasksDAOImpl();
            List<Map<String, Object>> tasks =  tasksDAO.getTasksByProjectId(Integer.parseInt(request.getParameter("id")));
            if (tasks != null) {
                tasks.forEach(resultTasks::add);
            }
            request.getSession().setAttribute("resultTasks", resultTasks);
        }
        else {
            resultTasks = (List<Map<String, Object>>) request.getSession().getAttribute("resultTasks");
        }
        Project project = dao.getProjectById(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("tasks", resultTasks);
        request.setAttribute("project", project);
        request.setAttribute("action", "editProject");

        request.getSession(true).setAttribute("path", "editProject?id=" + request.getParameter("id"));
        request.setAttribute("path", "editProject?id=" + request.getParameter("id"));
        request.getRequestDispatcher("projectView.jsp").forward(request, response);

    }
}

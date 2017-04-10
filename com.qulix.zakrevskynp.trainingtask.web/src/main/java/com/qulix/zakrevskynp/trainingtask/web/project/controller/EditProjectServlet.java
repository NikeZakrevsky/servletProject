package com.qulix.zakrevskynp.trainingtask.web.project.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.project.Project;
import com.qulix.zakrevskynp.trainingtask.web.project.ProjectDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.Task;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;

/**
 * Show view with form for editing new project and handling it data
 * @author Q-NZA
 */
@WebServlet("/editProject")
public class EditProjectServlet extends CustomProjectServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(EditProjectServlet.class.getName());
    private ProjectDAO dao = new ProjectDAOImpl();
    private List<Task> resultTasks;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Map<String, Object> parameters = getParametersFromRequest(request);
        ProjectDataValidator validator = new ProjectDataValidator();
        errors = validator.validate(parameters);
        if (errors.size() == 0) {
            Project project = parametersToObject(parameters);
            dao.updateProject(project);
            List<Task> resultTasks = (List<Task>)request.getSession().getAttribute("resultTasks");
            TasksDAOImpl tasksDAO = new TasksDAOImpl();
            List<Task> tasksList = tasksDAO.getTasksByProjectId(Integer.parseInt(request.getParameter("id")));
            Set<Object> t1 = tasksList.stream().map(task -> task.getId()).collect(Collectors.toSet());
            Set<Object> t2 = resultTasks.stream().map(task -> task.getId()).collect(Collectors.toSet());

            List<Map<String, Object>> removeList = new ArrayList<>();
            List<Map<String, Object>> updateList = new ArrayList<>();
            List<Map<String, Object>> addList = new ArrayList<>();

            tasksList.forEach(x -> {
                if (!t2.contains(x.getId())) {
                    tasksDAO.removeTask(x.getId());
                }
            });

            resultTasks.forEach(x -> {
                if (t1.contains(x.getId())) {
                    tasksDAO.updateTask(x);
                }
            });

            resultTasks.forEach(x -> {
                if (!t1.contains(x.getId())) {
                    tasksDAO.addTask(x);
                }
            });
            
            response.sendRedirect("projectsList");
        }
        else {
            request.setAttribute("project", parameters);
            List<Map<String, Object>> resultTasks = (List<Map<String, Object>>)request.getSession().getAttribute("resultTasks");
            request.setAttribute("tasks", resultTasks);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("projectView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("resultTasks") == null) {
            resultTasks = new ArrayList<>();
            TasksDAOImpl tasksDAO = new TasksDAOImpl();
            List<Task> tasks =  tasksDAO.getTasksByProjectId(Integer.parseInt(request.getParameter("id")));
            if (tasks != null) {
                resultTasks.addAll(tasks);
            }
            request.getSession().setAttribute("resultTasks", resultTasks);
        }
        else {
            resultTasks = (List<Task>) request.getSession().getAttribute("resultTasks");
        }
        if (request.getSession().getAttribute("project") == null) {
            Project project = dao.getProjectById(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("project", project);
        }
        else {
            request.setAttribute("project", request.getSession().getAttribute("project"));
        }
        request.setAttribute("tasks", resultTasks);
        request.getSession(true).setAttribute("path", "editProject?id=" + request.getParameter("id"));
        request.setAttribute("path", "editProject?id=" + request.getParameter("id"));
        request.getRequestDispatcher("projectView.jsp").forward(request, response);
    }
}

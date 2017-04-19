package com.qulix.zakrevskynp.trainingtask.web.controller.project;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Show view with form for editing new project and handling it data
 * @author Q-NZA
 */
@WebServlet("/editProject")
public class EditProjectServlet extends CustomProjectServlet {

    private ProjectDAO dao = new ProjectDAOImpl();
    private static final String ID = "id";
    private static final String EDIT_PROJECT = "editProject?id=";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> parameters = getParametersFromRequest(request);
        ProjectDataValidator validator = new ProjectDataValidator();
        List<String> errors = validator.validate(parameters);
        if (errors.isEmpty()) {
            Project project = parametersToObject(parameters);
            dao.updateProject(project);
            updateChangedTasks(request);
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
        List<Task> resultTasks;
        if (request.getSession().getAttribute(Attribute.RESULT_TASKS_LIST_NAME) == null) {
            resultTasks = new ArrayList<>();
            TasksDAOImpl tasksDAO = new TasksDAOImpl();
            List<Task> tasks =  tasksDAO.getTasksByProjectId(Integer.parseInt(request.getParameter(ID)));
            if (tasks != null) {
                resultTasks.addAll(tasks);
            }
            request.getSession().setAttribute(Attribute.RESULT_TASKS_LIST_NAME, resultTasks);
        }
        else {
            resultTasks = getItems(request.getSession().getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
        }
        if (request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME) == null) {
            Project project = dao.getProjectById(Integer.parseInt(request.getParameter(ID)));
            request.setAttribute(Attribute.PROJECT_OBJECT_NAME, project);
        }
        else {
            request.setAttribute(Attribute.PROJECT_OBJECT_NAME, request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME));
        }
        request.setAttribute(Attribute.TASKS_LIST_NAME, resultTasks);
        request.getSession(true).setAttribute(Attribute.PATH, EDIT_PROJECT + request.getParameter(ID));
        request.setAttribute(Attribute.PATH, EDIT_PROJECT + request.getParameter(ID));
        request.getRequestDispatcher(Attribute.PROJECT_VIEW).forward(request, response);
    }

    private void updateChangedTasks(HttpServletRequest request) {
        List<Task> resultTasks = getItems(request.getSession().getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
        TasksDAOImpl tasksDAO = new TasksDAOImpl();
        List<Task> tasksList = tasksDAO.getTasksByProjectId(Integer.parseInt(request.getParameter(ID)));
        Set<Object> t1 = tasksList.stream().map(Task::getId).collect(Collectors.toSet());
        Set<Object> t2 = resultTasks.stream().map(Task::getId).collect(Collectors.toSet());

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

    }

}

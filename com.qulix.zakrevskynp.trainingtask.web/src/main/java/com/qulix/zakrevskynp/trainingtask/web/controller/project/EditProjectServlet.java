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
import com.qulix.zakrevskynp.trainingtask.web.dao.ProjectDaoImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.TaskDaoImpl;
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
            Project project1 = new ProjectDaoImpl().get(Integer.parseInt(request.getParameter(ID)));
            project.setTasks(project1.getTasks());
            new ProjectDaoImpl().update(project);
            updateChangedTasks(request, project);
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
        ProjectDaoImpl projectDao = new ProjectDaoImpl();
        Project project = projectDao.get(Integer.parseInt(request.getParameter(ID)));
        if (request.getSession().getAttribute(Attribute.RESULT_TASKS_LIST_NAME) == null) {
            resultTasks = new ArrayList<>();
            List<Task> tasks =  project.getTasks();
            if (tasks != null) {
                resultTasks.addAll(tasks);
            }
            request.getSession().setAttribute(Attribute.RESULT_TASKS_LIST_NAME, resultTasks);
        }
        else {
            resultTasks = getItems(request.getSession().getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
        }
        if (request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME) == null) {
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

    private void updateChangedTasks(HttpServletRequest request, Project project) {
        List<Task> resultTasks = getItems(request.getSession().getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
        System.out.println("ResultTasks");
        for (Task resultTask : resultTasks) {
            System.out.println(resultTask);
        }
        System.out.println("Prev");
        TaskDaoImpl tasksDAO = new TaskDaoImpl();
        List<Task> tasksList = project.getTasks();
        for (Task task : tasksList) {
            System.out.println(task);
        }
        Set<Object> t1 = tasksList.stream().map(Task::getId).collect(Collectors.toSet());
        Set<Object> t2 = resultTasks.stream().map(Task::getId).collect(Collectors.toSet());
        for (Task task : tasksList) {
            if (!t2.contains(task.getId())) {
                tasksDAO.remove(task.getId());
            }    
        }
        for (Task resultTask : resultTasks) {
            if (t1.contains(resultTask.getId())) {
                tasksDAO.update(resultTask);
            }
        }
        for (Task resultTask : resultTasks) {
            if (!t1.contains(resultTask.getId())) {
                tasksDAO.add(resultTask);
            }
        }
    }

}

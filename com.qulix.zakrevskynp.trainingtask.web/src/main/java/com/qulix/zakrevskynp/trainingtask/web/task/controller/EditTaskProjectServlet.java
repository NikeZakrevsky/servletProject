package com.qulix.zakrevskynp.trainingtask.web.task.controller;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.person.Person;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.TaskDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;

@WebServlet("/editTaskProject")
public class EditTaskProjectServlet extends CustomServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(AddTaskProjectServlet.class.getName());
    private String returningPath;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("path", request.getSession().getAttribute("path").toString());
        returningPath = request.getSession(false).getAttribute("path").toString();
        List<Map<String, Object>> tasks = (List<Map<String, Object>>) request.getSession().getAttribute("resultTasks");
        Iterator iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> task = (Map<String, Object>) iterator.next();

            if ((Integer)task.get("id") == Integer.parseInt(request.getParameter("id"))) {
                try {
                    request.setAttribute("task", task);
                    request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
                    request.setAttribute("personsList", new PersonDAOImpl().getPersonsList());
                    request.setAttribute("isDisable", true);
                    request.getRequestDispatcher("taskView.jsp").forward(request, response);
                }
                catch (CustomException e) {
                    logger.log(Level.SEVERE, e.getCause().toString());
                    errors.clear();
                    errors.add(e.getMessage());
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("tasksList.jsp").forward(request, response);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        TaskDataValidator validator = new TaskDataValidator();
        Map<String, Object> parameters = getParametersFromRequest(request);

        TasksDAOImpl tasksDAO = new TasksDAOImpl();

        errors = validator.validate(parameters);
        try {
            if (errors.size() == 0) {
                tasksDAO.updateTask(parameters, request.getSession(), Integer.parseInt(request.getParameter("id")));
                response.sendRedirect(returningPath);
            }
            else {
                request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
                request.setAttribute("personsList", new PersonDAOImpl().getPersonsList());
                request.setAttribute("action", "editTaskProject");
                request.setAttribute("errors", errors);
                request.setAttribute("task", parameters);
                request.getRequestDispatcher("taskView.jsp").forward(request, response);
            }
        }
        catch (CustomException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("tasksView.jsp").forward(request, response);
        }
    }
}
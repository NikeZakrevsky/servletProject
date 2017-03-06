package com.qulix.zakrevskynp.trainingtask.web.controller.taskProject;

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

import com.qulix.zakrevskynp.trainingtask.web.controller.task.EditTaskServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.validator.TaskDataValidator;

@WebServlet("/taskProject")
public class EditTaskProjectServlet extends HttpServlet {

    private String returningPath = "tasksList";
    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(EditTaskServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String referrer = request.getHeader("referer");
        String[] splited = referrer.split("/");
        returningPath = splited[splited.length - 1];

        try {

            request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
            request.setAttribute("personsList", new PersonDAOImpl().getPersonsList());
            if (!request.getParameter("projectId").equals("")) {
                Map<String, Object> task = new HashMap<>();
                task.put("projectId", Integer.parseInt(request.getParameter("projectId")));
                request.setAttribute("task", task);
            }
            request.setAttribute("isDisable", true);
            request.setAttribute("action", "taskProject");
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        } catch (DAOException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("tasksList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<String> parametersNames = Collections.list(request.getParameterNames());
        Map<String, Object> parameters = parametersNames.stream().collect(Collectors.toMap(x -> x, request::getParameter));

        errors = new TaskDataValidator().validate(parameters);
        try {
            if (errors.size() == 0) {
                new TasksDAOImpl().addTask(parameters);
                response.sendRedirect(returningPath);
            }
            else {
                request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
                request.setAttribute("personsList",  new PersonDAOImpl().getPersonsList());
                request.setAttribute("action", "addTask");
                request.setAttribute("errors", errors);
                request.setAttribute("task", parameters);
                request.getRequestDispatcher("taskView.jsp").forward(request, response);
            }
        } catch (DAOException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("tasksList.jsp").forward(request, response);
        }
    }
}

package com.qulix.zakrevskynp.trainingtask.web.controller.project;

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

import com.qulix.zakrevskynp.trainingtask.web.controller.task.EditTaskServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.validator.TaskDataValidator;

@WebServlet("/taskProject")
public class TaskProject extends HttpServlet {

    private String returningPath = "tasksList";
    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(EditTaskServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String referrer = request.getHeader("referer");
        String[] splited = referrer.split("/");
        returningPath = splited[splited.length - 1];

        try {
            PersonDAO personDAO = new PersonDAOImpl();
            ProjectDAO projectDAO = new ProjectDAOImpl();

            request.setAttribute("projectsList", projectDAO.getProjectsList());
            request.setAttribute("personsList", personDAO.getPersonsList());
            if (!request.getParameter("projectId").equals("")) {
                request.setAttribute("projectShortName", projectDAO.getProjectById(Integer.parseInt(request.getParameter("projectId"))).getShortName());
            }
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

        if (errors.size() == 0) {
            try {
                new TasksDAOImpl().addTask(parameters);
            } catch (DAOException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
                errors.clear();
                errors.add(e.getMessage());
                request.setAttribute("error", errors);
                request.getRequestDispatcher("tasksList.jsp").forward(request, response);
            }
            response.sendRedirect(returningPath);
        }
        else {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        }
    }
}

package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.dao.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TaskUtil;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;
import com.qulix.zakrevskynp.trainingtask.web.validator.TaskDataValidator;

/**
 * Show view with form for adding new task and handling it data
 * @author Q-NZA
 */
@WebServlet("/addTask")
public class AddTaskServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private TasksDAO taskDAO = new TasksDAOImpl();
    private Logger logger = Logger.getLogger(AddTaskServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskDataValidator validator = new TaskDataValidator();
        TaskUtil taskUtil = new TaskUtil();
        Task task = new Task();
        task.setName(request.getParameter("name"));
        task.setTime(Integer.parseInt(request.getParameter("time")));
        try {
            task.setStartDate(taskUtil.toDate(request.getParameter("start_date")));
            task.setEndDate(taskUtil.toDate(request.getParameter("end_date")));
        } catch (ParseException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        task.setStatus(request.getParameter("status"));

        if (request.getParameter("projectId") != null) {
            task.setProjectId(Integer.parseInt(request.getParameter("projectId")));
        }
        else {
            task.setProjectId(null);
        }

        if (request.getParameter("personId") != null) {
            task.setPersonId(Integer.parseInt(request.getParameter("personId")));
        }
        else {
            task.setPersonId(null);
        }
        errors = validator.validate(task);

        if (errors.size() == 0) {
            try {
                taskDAO.addTask(task);
            } catch (DAOException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
                errors.clear();
                errors.add(e.getMessage());
                request.setAttribute("error", errors);
                request.getRequestDispatcher("tasksList.jsp").forward(request, response);
            }
            response.sendRedirect("tasksList");
        }
        else {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ProjectDAO projectDAO = new ProjectDAOImpl();
            List<Project> projectsList = projectDAO.getProjectsList();
            request.setAttribute("projectsList", projectsList);
            PersonDAO personDAO = new PersonDAOImpl();
            List<Person> personsList = personDAO.getPersonsList();
            request.setAttribute("personsList", personsList);
            request.setAttribute("action", "addTask");
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        } catch (DAOException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("tasksList.jsp").forward(request, response);
        }
    }
}

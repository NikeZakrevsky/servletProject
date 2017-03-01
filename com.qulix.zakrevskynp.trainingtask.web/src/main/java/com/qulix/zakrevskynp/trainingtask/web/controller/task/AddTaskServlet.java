package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TaskUtil;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;
import com.qulix.zakrevskynp.trainingtask.web.util.TaskDataValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Show view with form for adding new task and handling it data
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
        errors = validator.validate(request.getParameter("name"), request.getParameter("time"),
                request.getParameter("start_date"), request.getParameter("end_date"), request.getParameter("status"),
                request.getParameter("projectId"), request.getParameter("personId"), false);

        if (errors.size() == 0) {
            try {
                taskDAO.addTask(task);
            } catch (DAOException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
            }
            response.sendRedirect("tasksList");
        }
        else {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectDAO projectDAO = new ProjectDAOImpl();
        List<Project> projectsList = null;
        try {
            projectsList = projectDAO.getProjectsList();
        } catch (DAOException e) {
            request.setAttribute("error", errors);
            request.getRequestDispatcher("tasksList.jsp").forward(request, response);
            logger.log(Level.SEVERE, e.getCause().toString());
        }
        request.setAttribute("projectsList", projectsList);
        PersonDAO personDAO = new PersonDAOImpl();
        List<Person> personsList = null;
        try {
            personsList = personDAO.getPersonsList();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
        }
        request.setAttribute("personsList", personsList);
        request.setAttribute("action", "addTask");
        request.getRequestDispatcher("taskView.jsp").forward(request, response);
    }
}

package com.qulix.trainingtask.controller.task;

import com.qulix.trainingtask.dao.person.PersonDAO;
import com.qulix.trainingtask.dao.person.PersonDAOImpl;
import com.qulix.trainingtask.dao.exception.DAOException;
import com.qulix.trainingtask.dao.project.ProjectDAO;
import com.qulix.trainingtask.dao.project.ProjectDAOImpl;
import com.qulix.trainingtask.dao.task.TasksDAO;
import com.qulix.trainingtask.dao.task.TasksDAOImpl;
import com.qulix.trainingtask.model.Project;
import com.qulix.trainingtask.model.Task;
import com.qulix.trainingtask.util.TaskDataValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Show view with form for editing new person and handling it data
 */
@WebServlet("/editTask")
public class EditTaskServlet extends HttpServlet {

    private List<String> errors = new ArrayList<String>();
    private Logger logger = Logger.getLogger(EditTaskServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskDataValidator validator = new TaskDataValidator();
        errors = validator.validate(request.getParameter("name"), request.getParameter("time"), request.getParameter("start_date"), request.getParameter("end_date"), request.getParameter("status"), "", request.getParameter("personId"), true);
        if(errors.size() == 0) {
            TasksDAO tasksDAO = new TasksDAOImpl();
            try {
                tasksDAO.updateTask(Integer.parseInt(request.getParameter("id")), request.getParameter("name"), Integer.parseInt(request.getParameter("time")), request.getParameter("start_date"), request.getParameter("end_date"), request.getParameter("status"), request.getParameter("projectId"), request.getParameter("personId"));
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
            }
            response.sendRedirect("tasksList");
        } else {
            response.sendRedirect("editTask?id=" + request.getParameter("id"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TasksDAO taskDAO = new TasksDAOImpl();
        Task task = taskDAO.getTaskById(Integer.parseInt(request.getParameter("id")));
        PersonDAO personDAO = new PersonDAOImpl();
        ProjectDAO projectDAO = new ProjectDAOImpl();
        List<Project> projectsList = null;
        Project project = new Project();
        try {
            projectsList = projectDAO.getProjectsList();
            project = projectDAO.getProjectById(task.getProjectId());
        } catch (DAOException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
        }

        request.setAttribute("task", task);
        request.setAttribute("projectsList", projectsList);
        try {
            request.setAttribute("personsList", personDAO.getPersonsList());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
        }
        request.setAttribute("projectShortName", project.getShortName());
        request.setAttribute("errors", errors);
        request.setAttribute("action", "editTask");
        request.getRequestDispatcher("taskView.jsp").forward(request,response);
    }
}

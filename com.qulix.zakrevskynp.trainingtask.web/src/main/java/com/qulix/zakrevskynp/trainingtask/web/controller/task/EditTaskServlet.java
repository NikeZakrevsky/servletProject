package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TaskUtil;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
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
 * Show view with form for editing new person and handling it data
 */
@WebServlet("/editTask")
public class EditTaskServlet extends HttpServlet {

    private List<String> errors = new ArrayList<String>();
    private Logger logger = Logger.getLogger(EditTaskServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskDataValidator validator = new TaskDataValidator();
        TaskUtil taskUtil = new TaskUtil();
        Task task = new Task();
        task.setId(Integer.parseInt(request.getParameter("id")));
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
                request.getParameter("start_date"), request.getParameter("end_date"), request.getParameter("status"), "",
                request.getParameter("personId"), true);
        if (errors.size() == 0) {
            TasksDAO tasksDAO = new TasksDAOImpl();
            try {
                tasksDAO.updateTask(task);
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
            }
            response.sendRedirect("tasksList");
        } else {
            response.sendRedirect("editTask?id=" + request.getParameter("id"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            TasksDAO taskDAO = new TasksDAOImpl();
            PersonDAO personDAO = new PersonDAOImpl();
            ProjectDAO projectDAO = new ProjectDAOImpl();

            Task task = taskDAO.getTaskById(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("task", task);
            request.setAttribute("projectsList", projectDAO.getProjectsList());
            request.setAttribute("personsList", personDAO.getPersonsList());
            request.setAttribute("projectShortName", task.getProjectShortName());
            request.setAttribute("errors", errors);
            request.setAttribute("action", "editTask");
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        } catch (DAOException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
        }
    }
}

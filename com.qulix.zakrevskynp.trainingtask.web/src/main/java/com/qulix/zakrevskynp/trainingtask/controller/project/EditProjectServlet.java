package com.qulix.zakrevskynp.trainingtask.controller.project;

import com.qulix.zakrevskynp.trainingtask.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.dao.task.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.model.Project;
import com.qulix.zakrevskynp.trainingtask.model.Task;
import com.qulix.zakrevskynp.trainingtask.util.ProjectDataValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Show view with form for editing new project and handling it data
 */
@WebServlet("/editProject")
public class EditProjectServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(EditProjectServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectDAO dao = new ProjectDAOImpl();
        ProjectDataValidator validator = new ProjectDataValidator();
        errors = validator.validate(request.getParameter("name"), request.getParameter("shortName"), request.getParameter("description"));
        if(errors.size() == 0) {
            try {
                dao.updateProject(Integer.parseInt(request.getParameter("id")), request.getParameter("name"), request.getParameter("shortName"), request.getParameter("description"));
            } catch (DAOException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
                errors.clear();
                errors.add(e.getCause().getMessage());
                request.setAttribute("error",errors);
                request.getRequestDispatcher("projectList.jsp").forward(request,response);
            }
            response.sendRedirect("projectsList");
        }
        else {
            response.sendRedirect("editProject?id=" + request.getParameter("id"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectDAO dao = new ProjectDAOImpl();
        TasksDAO tasksDAO = new TasksDAOImpl();
        Project project = null;
        List<Task> tasks = new ArrayList<>();
        try {
            project = dao.getProjectById(Integer.parseInt(request.getParameter("id")));
            tasks = tasksDAO.getTasksList();
        } catch (DAOException e) {
            errors.clear();
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.add(e.getCause().getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("projectList.jsp").forward(request,response);
        }
        request.setAttribute("tasks", tasks);
        request.setAttribute("project",project);
        request.setAttribute("errors", errors);
        request.setAttribute("action","editProject");
        request.getRequestDispatcher("projectView.jsp").forward(request,response);
    }
}
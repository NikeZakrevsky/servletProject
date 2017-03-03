package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.io.IOException;
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
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;
import com.qulix.zakrevskynp.trainingtask.web.validator.ProjectDataValidator;

/**
 * Show view with form for editing new project and handling it data
 * @author Q-NZA
 */
@WebServlet("/editProject")
public class EditProjectServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(EditProjectServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ProjectDAO dao = new ProjectDAOImpl();
        ProjectDataValidator validator = new ProjectDataValidator();
        Project project = new Project();
        project.setId(Integer.parseInt(request.getParameter("id")));
        project.setName(request.getParameter("name"));
        project.setShortName(request.getParameter("shortName"));
        project.setDescription(request.getParameter("description"));
        errors = validator.validate(project);
        if (errors.size() == 0) {
            try {
                dao.updateProject(project);
            } catch (DAOException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
                errors.clear();
                errors.add(e.getMessage());
                request.setAttribute("error", errors);
                request.getRequestDispatcher("projectList.jsp").forward(request, response);
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
            tasks = tasksDAO.getTasksByProjectId(Integer.parseInt(request.getParameter("id")));
        } catch (DAOException e) {
            errors.clear();
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("projectList.jsp").forward(request, response);
        }
        request.setAttribute("tasks", tasks);
        request.setAttribute("project", project);
        request.setAttribute("errors", errors);
        request.setAttribute("action", "editProject");
        request.getRequestDispatcher("projectView.jsp").forward(request, response);
    }
}

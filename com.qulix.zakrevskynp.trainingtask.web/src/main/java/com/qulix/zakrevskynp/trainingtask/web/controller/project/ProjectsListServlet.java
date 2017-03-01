package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;

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
 * Show view with list of projects
 */
@WebServlet("/projectsList")
public class ProjectsListServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(ProjectsListServlet.class.getName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectDAO dao = new ProjectDAOImpl();
        List<Project> projectsList = null;
        try {
            projectsList = dao.getProjectsList();
        } catch (DAOException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getCause().getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("projectsList.jsp").forward(request, response);
        }
        request.setAttribute("projects", projectsList);
        request.getRequestDispatcher("projectsList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

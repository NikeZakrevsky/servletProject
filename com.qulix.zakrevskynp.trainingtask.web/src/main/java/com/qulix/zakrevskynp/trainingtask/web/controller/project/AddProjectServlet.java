package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.person.AddPersonServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.util.ProjectDataValidator;

/**
 * Show view with form for adding new project and handling it data
 * @author Q-NZA
 */
@WebServlet("/addProject")
public class AddProjectServlet extends HttpServlet {

    private ProjectDAO projectDAO = new ProjectDAOImpl();
    private Logger logger = Logger.getLogger(AddPersonServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors;
        ProjectDataValidator validator = new ProjectDataValidator();

        Project project = new Project();
        project.setName(request.getParameter("name"));
        project.setShortName(request.getParameter("shortName"));
        project.setDescription(request.getParameter("description"));

        errors = validator.validate(project);
        if (errors.size() == 0) {
            try {
                projectDAO.addProject(project);
            } catch (DAOException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
                errors.clear();
                errors.add(e.getCause().getMessage());
                request.setAttribute("error", errors);
                request.getRequestDispatcher("projectList.jsp").forward(request, response);
            }
            response.sendRedirect("projectsList");
        }
        else {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("projectView.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", "addProject");
        request.getRequestDispatcher("projectView.jsp").forward(request, response);
    }
}

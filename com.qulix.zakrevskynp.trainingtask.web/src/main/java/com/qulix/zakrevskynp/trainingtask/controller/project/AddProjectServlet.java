package com.qulix.zakrevskynp.trainingtask.controller.project;

import com.qulix.zakrevskynp.trainingtask.controller.person.AddPersonServlet;
import com.qulix.zakrevskynp.trainingtask.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.dao.exception.DAOException;
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
 * Show view with form for adding new project and handling it data
 */
@WebServlet("/addProject")
public class AddProjectServlet extends HttpServlet {

    private ProjectDAO projectDAO = new ProjectDAOImpl();
    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(AddPersonServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProjectDataValidator validator = new ProjectDataValidator();
        errors = validator.validate(request.getParameter("name"), request.getParameter("shortName"), request.getParameter("description"));
        if(errors.size() == 0) {
            try {
                projectDAO.addProject(request.getParameter("name"), request.getParameter("shortName"), request.getParameter("description"));
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
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("projectView.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action","addProject");
        request.getRequestDispatcher("projectView.jsp").forward(request, response);
    }
}

package com.qulix.trainingtask.controller.project;

import com.qulix.trainingtask.dao.person.PersonDAOImpl;
import com.qulix.trainingtask.dao.project.ProjectDAO;
import com.qulix.trainingtask.dao.project.ProjectDAOImpl;
import com.qulix.trainingtask.dao.exception.DAOException;

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
 * Handling remove project action
 */
@WebServlet("/removeProject")
public class RemoveProjectServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(RemoveProjectServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectDAO dao = new ProjectDAOImpl();
        try {
            dao.removeProject(Integer.parseInt(request.getParameter("id")));
        }
        catch(DAOException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getCause().getMessage());
            request.setAttribute("error",errors);
            request.getRequestDispatcher("projectList.jsp").forward(request,response);
            logger.log(Level.SEVERE, e.getCause().toString());
        }

        response.sendRedirect("projectsList");
    }
}

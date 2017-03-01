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

import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;

/**
 * Handling remove project action
 * @author Q-NZA
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
        catch (DAOException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getCause().getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("projectList.jsp").forward(request, response);
        }

        response.sendRedirect("projectsList");
    }
}

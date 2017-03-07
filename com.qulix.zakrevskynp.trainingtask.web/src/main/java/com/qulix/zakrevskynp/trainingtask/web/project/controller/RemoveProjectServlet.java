package com.qulix.zakrevskynp.trainingtask.web.project.controller;

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

import com.qulix.zakrevskynp.trainingtask.web.util.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;

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
            response.sendRedirect("projectsList");
        }
        catch (CustomException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("projectList.jsp").forward(request, response);
        }
    }
}

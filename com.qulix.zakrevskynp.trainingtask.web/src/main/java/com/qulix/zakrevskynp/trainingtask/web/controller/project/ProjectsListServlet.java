package com.qulix.zakrevskynp.trainingtask.web.controller.project;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;

/**
 * Show view with list of projects
 * @author Q-NZA
 */
@WebServlet("/projectsList")
public class ProjectsListServlet extends CustomServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        request.setAttribute("projects", new ProjectDAOImpl().getProjectsList());
        request.getRequestDispatcher("view/projectsList.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

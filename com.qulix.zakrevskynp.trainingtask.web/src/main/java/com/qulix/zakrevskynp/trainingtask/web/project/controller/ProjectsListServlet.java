package com.qulix.zakrevskynp.trainingtask.web.project.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;

/**
 * Show view with list of projects
 * @author Q-NZA
 */
@WebServlet("/projectsList")
public class ProjectsListServlet extends CustomServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(ProjectsListServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        request.setAttribute("projects", new ProjectDAOImpl().getProjectsList());
        request.getRequestDispatcher("projectsList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

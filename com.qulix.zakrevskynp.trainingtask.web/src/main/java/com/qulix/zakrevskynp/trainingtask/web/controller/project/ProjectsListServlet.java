package com.qulix.zakrevskynp.trainingtask.web.controller.project;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.ProjectDAOImpl;

/**
 * Shows a page with all projects from the database
 *
 * @author Q-NZA
 */
@WebServlet("/projectsList")
public class ProjectsListServlet extends CustomProjectServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDAOImpl().getAll());
        request.getRequestDispatcher(Attribute.PROJECTS_LIST_VIEW).forward(request, response);
    }
}

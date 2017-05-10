package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.ProjectDao;

/**
 * Shows a page with all projects from the database
 *
 * @author Q-NZA
 */
@WebServlet("/projectsList")
public class ProjectsListServlet extends CustomProjectServlet {
    /**
     * Displays a list of projects
     *
     * @param request http request with form data
     * @param response response object
     * @throws ServletException servlet exception
     * @throws IOException input/output exception
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDao().getAll());

        request.getRequestDispatcher(Attribute.PROJECTS_LIST_VIEW).forward(request, response);
    }
}

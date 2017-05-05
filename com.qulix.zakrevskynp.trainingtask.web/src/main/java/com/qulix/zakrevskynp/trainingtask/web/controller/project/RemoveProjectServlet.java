package com.qulix.zakrevskynp.trainingtask.web.controller.project;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.ProjectDao;

/**
 * Handling the action of removing a project and redirects to the @{{@link ProjectsListServlet}}
 *
 * @author Q-NZA
 */
@WebServlet("/removeProject")
public class RemoveProjectServlet extends CustomProjectServlet {

    private static final String ID = "id";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new ProjectDao().remove(Integer.parseInt(request.getParameter(ID)));

        response.sendRedirect(Attribute.REDIRECT_PROJECT_LIST);
    }
}

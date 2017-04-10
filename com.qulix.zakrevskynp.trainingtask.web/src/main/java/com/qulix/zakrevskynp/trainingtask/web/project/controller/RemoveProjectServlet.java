package com.qulix.zakrevskynp.trainingtask.web.project.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;

/**
 * Handling remove project action
 * @author Q-NZA
 */
@WebServlet("/removeProject")
public class RemoveProjectServlet extends CustomServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new ProjectDAOImpl().removeProject(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect("projectsList");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

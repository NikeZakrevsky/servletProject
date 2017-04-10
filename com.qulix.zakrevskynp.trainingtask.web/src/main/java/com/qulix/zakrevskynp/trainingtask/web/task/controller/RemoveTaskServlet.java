package com.qulix.zakrevskynp.trainingtask.web.task.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;

/**
 * Show remove task form and handling it data for removing task in database
 * @author Q-NZA
 */
@WebServlet("/removeTask")
public class RemoveTaskServlet extends CustomServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String returningPath = request.getSession(false).getAttribute("path").toString();
        TasksDAO tasksDAO = new TasksDAOImpl();
        tasksDAO.removeTask(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect(returningPath);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

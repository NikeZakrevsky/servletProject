package com.qulix.zakrevskynp.trainingtask.web.controller.task;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;

/**
 * Show list of all tasks in database
 * @author Q-NZA
 */
@WebServlet("/tasksList")
public class TasksListServlet extends CustomServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("path", "tasksList");
        request.setAttribute("tasks", new TasksDAOImpl().getTasksList());
        request.getRequestDispatcher("tasksList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

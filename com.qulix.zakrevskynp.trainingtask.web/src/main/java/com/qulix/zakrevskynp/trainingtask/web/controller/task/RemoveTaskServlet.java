package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;

/**
 * Handling remove task action
 * @author Q-NZA
 */
@WebServlet("/removeTask")
public class RemoveTaskServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TasksDAO tasksDAO = new TasksDAOImpl();
        tasksDAO.removeTask(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect("tasksList");
    }
}

package com.qulix.trainingtask.controller.task;

import com.qulix.trainingtask.dao.task.TasksDAO;
import com.qulix.trainingtask.dao.task.TasksDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handling remove task action
 */
@WebServlet("/removeTask")
public class RemoveTaskServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TasksDAO tasksDAO = new TasksDAOImpl();
        tasksDAO.removeTask(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect("tasksList");
    }
}

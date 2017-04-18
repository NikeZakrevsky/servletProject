package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.controller.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Show remove task form and handling it data for removing task in database
 * @author Q-NZA
 */
@WebServlet("/removeTask")
public class RemoveTaskServlet extends CustomServlet {
    private static final String ID = "id";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String returningPath = request.getSession(false).getAttribute(Attribute.PATH).toString();
        TasksDAO tasksDAO = new TasksDAOImpl(Task.class);
        tasksDAO.removeTask(Integer.parseInt(request.getParameter(ID)));
        response.sendRedirect(returningPath);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

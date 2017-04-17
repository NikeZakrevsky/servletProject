package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.controller.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;

/**
 * Show remove task form and handling it data for removing task in session
 * @author Q-NZA
 */
@WebServlet("/removeTaskProject")
public class RemoveTaskProjectServlet extends CustomServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("project", getParametersFromRequest(request));
        HttpSession session = request.getSession();
        String returningPath = request.getSession(false).getAttribute("path").toString();
        new TasksDAOImpl().removeTask(Integer.parseInt(request.getParameter("taskId")), session);
        session.setAttribute("resultTasks", session.getAttribute("resultTasks"));
        response.sendRedirect(returningPath);
    }
}
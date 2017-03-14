package com.qulix.zakrevskynp.trainingtask.web.task.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;

@WebServlet("/removeTaskProject")
public class RemoveTaskProjectServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String returningPath = request.getSession(false).getAttribute("path").toString();
        new TasksDAOImpl().removeTask(Integer.parseInt(request.getParameter("id")), session);
        session.setAttribute("resultTasks", session.getAttribute("resultTasks"));
        response.sendRedirect(returningPath);
    }
}

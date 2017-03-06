package com.qulix.zakrevskynp.trainingtask.web.controller.taskProject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/removeTaskProject")
public class RemoveTaskProjectServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Map<String, Object>> tasks = (List<Map<String, Object>>) session.getAttribute("tasks");
        tasks.removeIf(task -> (Integer) task.get("id") == Integer.parseInt(request.getParameter("id")));
        session.setAttribute("tasks", tasks);
        response.sendRedirect("addProject");
    }
}

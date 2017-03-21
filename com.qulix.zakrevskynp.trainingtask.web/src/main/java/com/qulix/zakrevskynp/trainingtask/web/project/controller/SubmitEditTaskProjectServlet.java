package com.qulix.zakrevskynp.trainingtask.web.project.controller;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;

@WebServlet("/editTaskProject1")
public class SubmitEditTaskProjectServlet extends CustomServlet {

    private String returningPath;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(getParametersFromRequest(request));
        request.getSession().setAttribute("project", getParametersFromRequest(request));
        request.setAttribute("path", request.getSession().getAttribute("path").toString());
        returningPath = request.getSession(false).getAttribute("path").toString();
        List<Map<String, Object>> tasks = (List<Map<String, Object>>) request.getSession().getAttribute("resultTasks");
        Iterator iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> task = (Map<String, Object>) iterator.next();

            if ((Integer)task.get("id") == Integer.parseInt(request.getParameter("taskId"))) {
                request.setAttribute("task", task);
                request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
                request.setAttribute("personsList", new PersonDAOImpl().getPersonsList());
                request.setAttribute("isDisable", true);
                request.setAttribute("action", "editTaskProject");
                request.getRequestDispatcher("taskView.jsp").forward(request, response);
            }
        }
    }
}

package com.qulix.zakrevskynp.trainingtask.web.task.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.project.dao.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.TaskDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;

@WebServlet("/taskProject")
public class AddTaskProjectServlet extends CustomServlet {

    private String returningPath;
    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(EditTaskServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        returningPath = request.getSession().getAttribute("path").toString();

        request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
        request.setAttribute("personsList", new PersonDAOImpl().getPersonsList());
        if (request.getParameter("projectId") != null) {
            Map<String, Object> task = new HashMap<>();
            task.put("projectId", Integer.parseInt(request.getParameter("projectId")));
            request.setAttribute("task", task);
        }
        request.setAttribute("isDisable", true);
        request.setAttribute("action", "taskProject");
        request.getRequestDispatcher("taskView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("isDisable", true);
        Map<String, Object> parameters = getParametersFromRequest(request);
        errors = new TaskDataValidator().validate(parameters);
        if (errors.size() == 0) {
            List<Map<String, Object>> resultTasks = new TasksDAOImpl().addTask(parameters, request.getSession());
            request.getSession().setAttribute("resultTasks", resultTasks);
            response.sendRedirect(returningPath);
        }
        else {
            request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
            request.setAttribute("personsList",  new PersonDAOImpl().getPersonsList());
            request.setAttribute("action", "taskProject");
            request.setAttribute("errors", errors);
            request.setAttribute("task", parameters);
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        }
    }
}
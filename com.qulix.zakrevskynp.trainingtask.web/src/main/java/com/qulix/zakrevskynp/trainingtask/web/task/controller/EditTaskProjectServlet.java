package com.qulix.zakrevskynp.trainingtask.web.task.controller;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.person.Person;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.TaskDataValidator;

@WebServlet("/editTaskProject")
public class EditTaskProjectServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(AddTaskProjectServlet.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, Object>> tasks = (List<Map<String, Object>>) request.getSession().getAttribute("tasks");
        Iterator iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> task = (Map<String, Object>) iterator.next();

            if ((Integer)task.get("id") == Integer.parseInt(request.getParameter("id"))) {
                try {
                    request.setAttribute("task", task);
                    request.setAttribute("personsList", new PersonDAOImpl().getPersonsList());
                    request.setAttribute("isDisable", true);
                    request.getRequestDispatcher("taskView.jsp").forward(request, response);
                }
                catch (CustomException e) {
                    logger.log(Level.SEVERE, e.getCause().toString());
                    errors.clear();
                    errors.add(e.getMessage());
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("tasksList.jsp").forward(request, response);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        TaskDataValidator validator = new TaskDataValidator();
        List<String> parametersNames = Collections.list(request.getParameterNames());
        Map<String, Object> parameters = parametersNames.stream().collect(Collectors.toMap(x -> x, request::getParameter));

        errors = validator.validate(parameters);
        List<Map<String, Object>> tasks = (List<Map<String, Object>>) request.getSession().getAttribute("tasks");
        if (errors.size() == 0) {

            Iterator iterator = tasks.iterator();
            int i = 0;
            while (iterator.hasNext()) {
               Map<String, Object> task = (Map<String, Object>) iterator.next();
               if ((Integer)task.get("id") == Integer.parseInt(request.getParameter("id"))) {
                   try {
                       parameters.put("id", Integer.parseInt(parameters.get("id").toString()));
                       if (parameters.get("personId") != null) {
                           Person person = new PersonDAOImpl().getPersonById((int) parameters.get("personId"));
                           parameters.put("performer", person.getFname() + " " + person.getSname() + " " + person.getLname());
                       }
                       tasks.set(i, parameters);
                       response.sendRedirect("addProject");
                   }
                   catch (CustomException e) {
                       logger.log(Level.SEVERE, e.getCause().toString());
                       errors.clear();
                       errors.add(e.getMessage());
                       request.setAttribute("error", errors);
                       request.getRequestDispatcher("tasksView.jsp").forward(request, response);
                   }
               }
               i++;
           }
        }
    }
}

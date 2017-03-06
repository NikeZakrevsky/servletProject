package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.controller.person.AddPersonServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.validator.ProjectDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.validator.TaskDataValidator;

/**
 * Show view with form for adding new project and handling it data
 * @author Q-NZA
 */
@WebServlet("/addProject")
public class AddProjectServlet extends HttpServlet {

    private ProjectDAO projectDAO = new ProjectDAOImpl();
    private Logger logger = Logger.getLogger(AddPersonServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ProjectDataValidator validator = new ProjectDataValidator();
        TaskDataValidator taskDataValidator = new TaskDataValidator();
        List<String> parametersNames = Collections.list(request.getParameterNames());
        Map<String, Object> parameters = parametersNames.stream().collect(Collectors.toMap(x -> x, request::getParameter));

        List<String> errors = validator.validate(parameters);
        if (errors.size() == 0) {
            try {
                int id = projectDAO.addProject(parameters);
                TasksDAO tasksDAO = new TasksDAOImpl();
                List<Map<String, Object>> tasks = (List<Map<String, Object>>)request.getSession().getAttribute("tasks");
                Iterator<Map<String, Object>> iterator = tasks.iterator();
                if (tasks.size() != 0) {
                    while(iterator.hasNext()) {
                        Map<String, Object> task = iterator.next();
                        task.put("projectId", id);
                        taskDataValidator.validate(task);
                        tasksDAO.addTask(task);
                    }
                }
                request.getSession().invalidate();
            } catch (DAOException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
                errors.clear();
                errors.add(e.getMessage());
                request.setAttribute("error", errors);
                request.getRequestDispatcher("projectsList.jsp").forward(request, response);
            }
            response.sendRedirect("projectsList");
        }
        else {
            request.setAttribute("project", parameters);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("projectView.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", "addProject");
        HttpSession session = request.getSession();
        List<Map<String, Object>> tasks = (List<Map<String, Object>>)session.getAttribute("tasks");
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("projectView.jsp").forward(request, response);
    }
}

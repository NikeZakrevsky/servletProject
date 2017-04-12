package com.qulix.zakrevskynp.trainingtask.web.controller.task;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.project.ProjectDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;

/**
 * Show edit task form and handling form data for editing task in session
 * @author Q-NZA
 */
@WebServlet("/editTaskProject")
public class EditTaskProjectServlet extends CustomTaskServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TaskDataValidator validator = new TaskDataValidator();
        Map<String, Object> parameters = getParametersFromRequest(request);

        String returningPath = request.getSession().getAttribute("path").toString();
        List<String> errors = validator.validate(parameters);
        Task task = parametersToObject(parameters);
        if (errors.size() == 0) {
            TasksDAOImpl tasksDAO = new TasksDAOImpl();
            tasksDAO.updateTask(task, request.getSession(), Integer.parseInt(request.getParameter("id")));
            response.sendRedirect(returningPath);
        }
        else {
            request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
            request.setAttribute("personsList", new PersonDAOImpl().getPersonsList());
            request.setAttribute("action", "editTaskProject");
            request.setAttribute("errors", errors);
            request.setAttribute("task", task);
            request.getRequestDispatcher("view/taskView.jsp").forward(request, response);
        }
    }
}
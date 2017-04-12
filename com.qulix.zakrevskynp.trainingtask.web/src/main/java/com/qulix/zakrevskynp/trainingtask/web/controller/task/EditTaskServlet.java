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
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;

/**
 * Show edit task form and handling it data for editing task in session
 * @author Q-NZA
 */
@WebServlet("/editTask")
public class EditTaskServlet extends CustomTaskServlet {

    private String returningPath;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TaskDataValidator validator = new TaskDataValidator();
        Map<String, Object> parameters = getParametersFromRequest(request);

        List<String> errors = validator.validate(parameters);
        Task task = parametersToObject(parameters);
        if (errors.size() == 0) {
            new TasksDAOImpl().updateTask(task);
            response.sendRedirect(returningPath);
        } else {
            request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
            request.setAttribute("personsList",  new PersonDAOImpl().getPersonsList());
            request.setAttribute("action", "editTask");
            request.setAttribute("errors", errors);
            request.setAttribute("task", task);
            request.getRequestDispatcher("view/taskView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("path", request.getSession().getAttribute("path").toString());
        returningPath = request.getSession(false).getAttribute("path").toString();
        TasksDAO taskDAO = new TasksDAOImpl();
        Task task = taskDAO.getTaskById(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("task", task);
        request.setAttribute("projectsList", new ProjectDAOImpl().getProjectsList());
        request.setAttribute("personsList",  new PersonDAOImpl().getPersonsList());
        request.setAttribute("action", "editTask");
        if (!returningPath.equals("tasksList")) {
            request.setAttribute("isDisable", true);
        }
        request.getRequestDispatcher("view/taskView.jsp").forward(request, response);
    }
}

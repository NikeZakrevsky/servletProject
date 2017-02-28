package com.qulix.zakrevskynp.trainingtask.controller.task;

import com.qulix.zakrevskynp.trainingtask.dao.task.TasksDAO;
import com.qulix.zakrevskynp.trainingtask.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Show view with list if tasks
 */
@WebServlet("/tasksList")
public class TasksListServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(TasksListServlet.class.getName());

    private List<String> errors = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TasksDAO tasksDAO = new TasksDAOImpl();
        List<Task> tasksList = null;
        try {
            tasksList = tasksDAO.getTasksList();
        } catch (DAOException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getCause().getMessage());
            request.setAttribute("error",errors);
            request.getRequestDispatcher("tasksList.jsp").forward(request,response);
        }
        String sortField = request.getParameter("sortField");
        request.setAttribute("sortF", sortField);
        request.setAttribute("tasks", tasksList);
        request.getRequestDispatcher("tasksList.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

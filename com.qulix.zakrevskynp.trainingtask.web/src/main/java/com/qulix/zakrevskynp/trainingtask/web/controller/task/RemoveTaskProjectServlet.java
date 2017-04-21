package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.controller.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.TaskDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Remove task from session
 * @author Q-NZA
 */
@WebServlet("/removeTaskProject")
public class RemoveTaskProjectServlet extends CustomServlet {

    private static final String TASK_ID = "taskId";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(Attribute.PROJECT_OBJECT_NAME, getParametersFromRequest(request));
        HttpSession session = request.getSession();
        String returningPath = request.getSession(false).getAttribute(Attribute.PATH).toString();
        List<Task> tasks = getItems(session.getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
        List<Task> resultTasks = new TaskDAOImpl().removeTask(Integer.parseInt(request.getParameter(TASK_ID)), tasks);
        session.setAttribute(Attribute.RESULT_TASKS_LIST_NAME, resultTasks);
        session.setAttribute(Attribute.RESULT_TASKS_LIST_NAME, session.getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
        response.sendRedirect(returningPath);
    }
}
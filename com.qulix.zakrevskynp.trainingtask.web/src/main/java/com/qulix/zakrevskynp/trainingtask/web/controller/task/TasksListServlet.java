package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.controller.BaseHttpServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.TaskDao;

/**
 * Shows a page with all tasks from the database.
 *
 * @author Q-NZA
 */
@WebServlet("/tasksList")
public class TasksListServlet extends BaseHttpServlet {
    /**
     * Displays a list of tasks.
     *
     * @param request http request with form data.
     * @param response response object.
     * @throws ServletException servlet exception.
     * @throws IOException input/output exception.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(Attribute.PATH, Attribute.TASKS_LIST);
        request.setAttribute(Attribute.TASKS_LIST_NAME, new TaskDao().getAll());

        request.getRequestDispatcher(Attribute.TASKS_LIST_VIEW).forward(request, response);
    }
}

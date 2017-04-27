package com.qulix.zakrevskynp.trainingtask.web.controller.task;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.controller.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.TaskDaoImpl;

/**
 * Shows a page with all tasks from the database
 *
 * @author Q-NZA
 */
@WebServlet("/tasksList")
public class TasksListServlet extends CustomServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(Attribute.PATH, Attribute.TASKS_LIST);
        request.setAttribute(Attribute.TASKS_LIST_NAME, new TaskDaoImpl().getAll());
        request.getRequestDispatcher(Attribute.TASKS_LIST_VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

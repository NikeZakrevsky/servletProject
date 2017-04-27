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
 * Handling the action of removing a task and redirects to the @{{@link TasksListServlet}}
 *
 * @author Q-NZA
 */
@WebServlet("/removeTask")
public class RemoveTaskServlet extends CustomServlet {

    private static final String ID = "id";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String returningPath = request.getSession(false).getAttribute(Attribute.PATH).toString();
        new TaskDaoImpl().remove(Integer.parseInt(request.getParameter(ID)));
        response.sendRedirect(returningPath);
    }
}

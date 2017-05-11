package com.qulix.zakrevskynp.trainingtask.web.controller.task;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDao;
import com.qulix.zakrevskynp.trainingtask.web.dao.ProjectDao;
import com.qulix.zakrevskynp.trainingtask.web.dao.TaskDao;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * The servlet displays the form of adding a task and processes the entered data.
 *
 * @author Q-NZA
 */
@WebServlet("/addTask")
public class AddTaskServlet extends CustomTaskServlet {

    /**
     * The method receives data from a form, validates it and adds the task's data in the database.
     * In the case of validation errors, its displayed to the user.
     * If there are no validation errors, the list of persons is displayed.
     *
     * @param request http request with form data.
     * @param response response object.
     * @throws ServletException servlet exception.
     * @throws IOException input/output exception.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskDataValidator taskDataValidator = new TaskDataValidator();
        List<String> errors = taskDataValidator.validate(request);

        if (errors.isEmpty()) {
            Task task = parametersToObject(request);
            new TaskDao().add(task);

            response.sendRedirect(Attribute.TASKS_LIST);
        }
        else {
            request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDao().getAll());
            request.setAttribute(Attribute.PERSONS_LIST_NAME,  new PersonDao().getAll());
            request.setAttribute(Attribute.ACTION, Attribute.ADD_TASK);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            setAttributesToRequest(request);

            request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
        }
    }

    /**
     * The method displays a form for adding a task
     *
     * @param request http request with form data.
     * @param response response object.
     * @throws ServletException servlet exception.
     * @throws IOException input/output exception.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDao().getAll());
        request.setAttribute(Attribute.PERSONS_LIST_NAME,  new PersonDao().getAll());
        request.setAttribute(Attribute.ACTION, Attribute.ADD_TASK);
        request.setAttribute(Attribute.PATH, Attribute.TASKS_LIST);

        request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
    }
}

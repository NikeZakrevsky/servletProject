package com.qulix.zakrevskynp.trainingtask.web.controller.task;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDao;
import com.qulix.zakrevskynp.trainingtask.web.dao.ProjectDao;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Shows the form of adding a new task in session
 *
 * @author Q-NZA
 */
@WebServlet("/taskProject")
public class AddTaskProjectServlet extends CustomTaskServlet {

    private static final String IS_DISABLE = "isDisable";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(IS_DISABLE, true);

        Map<String, Object> parameters = getParametersFromRequest(request);

        TaskDataValidator taskDataValidator = new TaskDataValidator();
        List<String> errors = taskDataValidator.validate(parameters);

        if (errors.isEmpty()) {
            Task task = parametersToObject(parameters);
            Project project = (Project) request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME);
            List<Task> resultTasks = addTaskToList(task, project.getTasks());
            project.setTasks(resultTasks);
            request.getSession().setAttribute(Attribute.PROJECT_OBJECT_NAME, project);

            response.sendRedirect(request.getSession().getAttribute(Attribute.PATH).toString());
        }
        else {
            request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDao().getAll());
            request.setAttribute(Attribute.PERSONS_LIST_NAME,  new PersonDao().getAll());
            request.setAttribute(Attribute.ACTION, Attribute.TASK_PROJECT);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            request.setAttribute(Attribute.TASK_OBJECT_NAME, parameters);

            request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
        }
    }

    /**
     * Insert task in List
     *
     * @param task data from add task form
     * @return list of tasks with added new task
     */
    private List<Task> addTaskToList(Task task, List<Task> tasks) {
        int id = 0;
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        for (Task task1 : tasks) {
            if (task1.getId() > id) {
                id = task1.getId();
            }
        }
        task.setId(id + 1);
        tasks.add(task);
        return tasks;
    }
}
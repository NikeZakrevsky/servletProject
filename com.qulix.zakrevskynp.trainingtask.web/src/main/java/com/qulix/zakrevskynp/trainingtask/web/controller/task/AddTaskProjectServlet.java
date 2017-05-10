package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Form data processing
     *
     * @param request http request with form data
     * @param response response object
     * @throws ServletException servlet exception
     * @throws IOException input/output exception
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(IS_DISABLE, true);

        TaskDataValidator taskDataValidator = new TaskDataValidator();
        List<String> errors = taskDataValidator.validate(request);

        if (errors.isEmpty()) {
            Task task = parametersToObject(request);
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
            setAttributesToRequest(request);

            request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
        }
    }

    private List<Task> addTaskToList(Task task, List<Task> tasks) {
        ProjectDao projectDao = new ProjectDao();
        if (task.getProjectId() != null) {
            String shortName = projectDao.get(task.getProjectId()).getShortName();
            task.setProjectShortName(shortName);
        }
        task.setStartDate(new java.sql.Date(task.getStartDate().getTime()));
        task.setEndDate(new java.sql.Date(task.getEndDate().getTime()));
        int id = 0;
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        for (Task newTask : tasks) {
            if (newTask.getId() > id) {
                id = newTask.getId();
            }
        }
        task.setId(id + 1);
        tasks.add(task);

        return tasks;
    }
}
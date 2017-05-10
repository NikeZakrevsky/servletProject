package com.qulix.zakrevskynp.trainingtask.web.controller.project;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.controller.task.CustomTaskServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDao;
import com.qulix.zakrevskynp.trainingtask.web.dao.ProjectDao;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Saves data from the project update form
 *
 * @author Q-NZA
 */
@WebServlet("/editTaskProject1")
public class SubmitEditTaskProjectServlet extends CustomProjectServlet {

    private static final String IS_DISABLE = "isDisable";
    private static final String TASK_ID = "taskId";
    private static final String EDIT_TASK_PROJECT = "editTaskProject";

    /**
     * Form data processing
     *
     * @param request http request with form data
     * @param response response object
     * @throws ServletException servlet exception
     * @throws IOException input/output exception
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectDataValidator projectDataValidator = new ProjectDataValidator();
        projectDataValidator.validate(request);

        Project newProject = parametersToObject(request);
        Project project = (Project) request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME);
        project.setName(newProject.getName());
        project.setShortName(newProject.getShortName());
        project.setDescription(project.getDescription());

        request.getSession().setAttribute(Attribute.PROJECT_OBJECT_NAME, project);
        request.setAttribute(Attribute.PATH, request.getSession().getAttribute(Attribute.PATH).toString());

        List<Task> tasks = project.getTasks();
        for (Task task : tasks) {
            if (task.getId() == Integer.parseInt(request.getParameter(TASK_ID))) {
                request.setAttribute(Attribute.TASK_OBJECT_NAME, task);
                request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDao().getAll());
                request.setAttribute(Attribute.PERSONS_LIST_NAME, new PersonDao().getAll());
                request.setAttribute(IS_DISABLE, true);
                request.setAttribute(Attribute.ACTION, EDIT_TASK_PROJECT);
                new CustomTaskServlet().setObjectToRequest(task, request);

                request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
            }
        }
    }
}

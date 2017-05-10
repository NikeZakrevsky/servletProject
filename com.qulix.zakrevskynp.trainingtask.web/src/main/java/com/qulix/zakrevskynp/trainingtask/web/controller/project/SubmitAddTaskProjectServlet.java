package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.io.IOException;
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
 * Saves data from the project adding form
 *
 * @author Q-NZA
 */
@WebServlet("/taskProject1")
public class SubmitAddTaskProjectServlet extends CustomProjectServlet {

    private static final String ID = "id";
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
        ProjectDataValidator projectDataValidator = new ProjectDataValidator();
        projectDataValidator.validate(request);

        Project newProject = parametersToObject(request);
        Project project = (Project) request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME);
        if (project != null) {
            List<Task> tasks = project.getTasks();
            if (tasks != null) {
                newProject.setTasks(tasks);
            }
        }

        request.getSession().setAttribute(Attribute.PROJECT_OBJECT_NAME, newProject);
        request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDao().getAll());
        request.setAttribute(Attribute.PERSONS_LIST_NAME, new PersonDao().getAll());
        if (!request.getParameter(ID).equals("")) {
            request.setAttribute("projectId", Integer.parseInt(request.getParameter(ID)));
        }
        request.setAttribute(IS_DISABLE, true);
        request.setAttribute(Attribute.ACTION, Attribute.TASK_PROJECT);

        request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
    }
}

package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.controller.project.CustomProjectServlet;
import com.qulix.zakrevskynp.trainingtask.web.controller.project.ProjectDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Removes the task from the session
 *
 * @author Q-NZA
 */
@WebServlet("/removeTaskProject")
public class RemoveTaskProjectServlet extends CustomProjectServlet {

    private static final String TASK_ID = "taskId";

    /**
     * Form data processing
     *
     * @param request http request with form data
     * @param response response object
     * @throws ServletException servlet exception
     * @throws IOException input/output exception
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> parametersFromRequest = getParametersFromRequest(request);

        ProjectDataValidator projectDataValidator = new ProjectDataValidator();
        projectDataValidator.validate(parametersFromRequest);

        Project newProject = parametersToObject(parametersFromRequest);
        Project project = (Project) request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME);
        List<Task> tasks = project.getTasks();
        newProject.setTasks(tasks);
        List<Task> resultTasks = removeTask(Integer.parseInt(request.getParameter(TASK_ID)), tasks);
        project.setTasks(resultTasks);

        request.getSession().setAttribute(Attribute.PROJECT_OBJECT_NAME, newProject);
        request.setAttribute(Attribute.PROJECT_OBJECT_NAME, newProject);
        String returningPath = request.getSession().getAttribute(Attribute.PATH).toString();

        response.sendRedirect(returningPath);
    }

    private List<Task> removeTask(int id, List<Task> tasks) {
        tasks.removeIf(task -> task.getId() == id);
        return tasks;
    }

}
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
import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * The servlet displays the form of updating a task and updates the entered data in the session.
 *
 * @author Q-NZA
 */
@WebServlet("/editTaskProject")
public class EditTaskProjectServlet extends CustomTaskServlet {
    private static final String ID = "id";

    /**
     * The method receives data from a form, validates it and updates the task's data in the session.
     * In the case of validation errors, its displayed to the user.
     * If there are no validation errors, the edit project form is displayed.
     *
     * @param request http request with form data.
     * @param response response object.
     * @throws ServletException servlet exception.
     * @throws IOException input/output exception.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String returningPath = request.getSession().getAttribute(Attribute.PATH).toString();

        TaskDataValidator validator = new TaskDataValidator();
        List<String> errors = validator.validate(request);

        if (errors.isEmpty()) {
            Task task = parametersToObject(request);
            Project project = (Project) request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME);
            List<Task> resultTasks = updateTaskInSessionList(task, project.getTasks(), Integer.parseInt(request.
                getParameter(ID)));
            project.setTasks(resultTasks);
            request.getSession().setAttribute(Attribute.PROJECT_OBJECT_NAME, project);

            response.sendRedirect(returningPath);
        }
        else {
            request.setAttribute(Attribute.PROJECTS_LIST_NAME, new ProjectDao().getAll());
            request.setAttribute(Attribute.PERSONS_LIST_NAME, new PersonDao().getAll());
            request.setAttribute(Attribute.ACTION, Attribute.EDIT_TASK_PROJECT);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            request.setAttribute(Attribute.TASK_OBJECT_NAME, request);

            request.getRequestDispatcher(Attribute.TASK_VIEW).forward(request, response);
        }
    }

    private void setPerformer(Task task) {
        if (task.getPerson() != null) {
            Person person = new PersonDao().get(task.getPerson().getId());
            task.setPerson(person);
        }
    }

    /**
     * The method sets the task performer and updates it in the session.
     *
     * @param task task for updating.
     * @param tasks task list.
     * @param id task id.
     * @return task list with updated task.
     */
    private List<Task> updateTaskInSessionList(Task task, List<Task> tasks, int id) {
        int index = 0;
        task.setStartDate(new java.sql.Date(task.getStartDate().getTime()));
        task.setEndDate(new java.sql.Date(task.getEndDate().getTime()));
        for (Task newTask : tasks) {
            if (newTask.getId() == id) {
                setPerformer(task);
                index = tasks.indexOf(newTask);
                break;
            }
        }
        tasks.set(index, task);

        return tasks;
    }
}
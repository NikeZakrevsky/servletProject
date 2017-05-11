package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.ProjectDao;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * The servlet displays the form of updating a project and processes the entered data.
 *
 * @author Q-NZA
 */
@WebServlet("/editProject")
public class EditProjectServlet extends CustomProjectServlet {
    private static final String ID = "id";
    private static final String EDIT_PROJECT = "editProject?id=";

    /**
     * The method receives data from a form, validates it and updates the project's data in the database.
     * In the case of validation errors, its displayed to the user.
     * If there are no validation errors, the list of persons is displayed.
     *
     * @param request http request with form data.
     * @param response response object.
     * @throws ServletException servlet exception.
     * @throws IOException input/output exception.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectDataValidator validator = new ProjectDataValidator();
        List<String> errors = validator.validate(request);
        
        if (errors.isEmpty()) {
            Project project = parametersToObject(request);
            Project newProject = (Project) request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME);
            if (newProject != null) {
                project.setTasks(newProject.getTasks());
            }

            new ProjectDao().update(project);

            response.sendRedirect(Attribute.REDIRECT_PROJECT_LIST);
        }
        else {
            setAttributesToRequest(request);
            HttpSession session = request.getSession();
            List<Task> resultTasks = getItems(session.getAttribute(Attribute.RESULT_TASKS_LIST_NAME));
            request.setAttribute(Attribute.TASKS_LIST_NAME, resultTasks);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);

            request.getRequestDispatcher(Attribute.PROJECT_VIEW).forward(request, response);
        }
    }

    /**
     * The method displays a form for updating a project.
     *
     * @param request http request with form data.
     * @param response response object.
     * @throws ServletException servlet exception.
     * @throws IOException input/output exception.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Project project;
        if (request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME) == null) {
            ProjectDao projectDao = new ProjectDao();
            project = projectDao.get(Integer.parseInt(request.getParameter(ID)));
            request.getSession().setAttribute(Attribute.PROJECT_OBJECT_NAME, project);
        }
        else {
            project = (Project) request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME);
        }
        request.setAttribute(Attribute.PROJECT_OBJECT_NAME, project);
        request.getSession().setAttribute(Attribute.PATH, EDIT_PROJECT + request.getParameter(ID));
        request.setAttribute(Attribute.PATH, EDIT_PROJECT + request.getParameter(ID));

        request.getRequestDispatcher(Attribute.PROJECT_VIEW).forward(request, response);
    }

}

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

/**
 * Shows the form of adding a new project and processing its data
 *
 * @author Q-NZA
 */
@WebServlet("/addProject")
public class AddProjectServlet extends CustomProjectServlet {

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
        List<String> errors = projectDataValidator.validate(request);

        if (errors.isEmpty()) {
            Project newProject = parametersToObject(request);
            Project project = (Project) request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME);
            if (project != null) {
                newProject.setTasks(project.getTasks());
            }
            new ProjectDao().add(newProject);
            request.getSession().invalidate();

            response.sendRedirect(Attribute.REDIRECT_PROJECT_LIST);
        }
        else {
            HttpSession session = request.getSession();
            Project project = (Project) session.getAttribute(Attribute.PROJECT_OBJECT_NAME);
            
            setAttributesToRequest(request);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            request.setAttribute(Attribute.PROJECT_OBJECT_NAME, project);

            request.getRequestDispatcher(Attribute.PROJECT_VIEW).forward(request, response);
        }
    }

    /**
     * Displays a page with a form
     *
     * @param request http request with form data
     * @param response response object
     * @throws ServletException servlet exception
     * @throws IOException input/output exception
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Project project = (Project) session.getAttribute(Attribute.PROJECT_OBJECT_NAME);
        session.setAttribute(Attribute.PATH,  Attribute.ADD_PROJECT);

        request.setAttribute(Attribute.ACTION, Attribute.ADD_PROJECT);
        request.setAttribute(Attribute.PATH, Attribute.ADD_PROJECT);
        request.setAttribute(Attribute.PROJECT_OBJECT_NAME, project);

        request.getRequestDispatcher(Attribute.PROJECT_VIEW).forward(request, response);
    }
}

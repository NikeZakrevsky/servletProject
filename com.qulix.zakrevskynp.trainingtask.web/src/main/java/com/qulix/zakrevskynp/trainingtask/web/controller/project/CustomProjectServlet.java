package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import javax.servlet.http.HttpServletRequest;

import com.qulix.zakrevskynp.trainingtask.web.controller.BaseHttpServlet;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;

/**
 * Custom servlet for constructing a @{{@link Project}} object from Map
 *
 * @author Q-NZA
 */
public class CustomProjectServlet extends BaseHttpServlet {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SHORT_NAME = "shortName";
    private static final String DESCRIPTION = "description";

    /**
     * Converts a map with request parameters to a @{{@link Project}} object
     *
     * @param parameters map with request parameters
     * @return @{{@link Project}} object
     */
    protected Project parametersToObject(HttpServletRequest parameters) {
        Integer id = null;
        if (!parameters.getParameter(ID).equals("")) {
            id = Integer.parseInt(parameters.getParameter(ID));
        }
        String name = parameters.getParameter(NAME);
        String shortName = parameters.getParameter(SHORT_NAME);
        String description = parameters.getParameter(DESCRIPTION);

        return new Project(id, name, shortName, description);
    }

    protected void setAttributesToRequest(HttpServletRequest request) {
        request.setAttribute(ID, request.getParameter(ID));
        request.setAttribute(NAME, request.getParameter(NAME));
        request.setAttribute(SHORT_NAME, request.getParameter(SHORT_NAME));
        request.setAttribute(DESCRIPTION, request.getParameter(DESCRIPTION));
    }
}

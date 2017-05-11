package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import javax.servlet.http.HttpServletRequest;

import com.qulix.zakrevskynp.trainingtask.web.controller.BaseHttpServlet;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;

/**
 * The servlet contains common methods for *Project servlets.
 *
 * @author Q-NZA
 */
public class CustomProjectServlet extends BaseHttpServlet {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SHORT_NAME = "shortName";
    private static final String DESCRIPTION = "description";

    /**
     * The method creates the @{{@link Project}} object from the http request parameters.
     *
     * @param parameters map with request parameters.
     * @return @{{@link Project}} object.
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

    /**
     * The method sets the attributes of the http request.
     * The method is used if parameters from the request are not valid.
     *
     * @param request @{{@link HttpServletRequest}} object from setting of the attributes.
     */
    protected void setAttributesToRequest(HttpServletRequest request) {
        request.setAttribute(ID, request.getParameter(ID));
        request.setAttribute(NAME, request.getParameter(NAME));
        request.setAttribute(SHORT_NAME, request.getParameter(SHORT_NAME));
        request.setAttribute(DESCRIPTION, request.getParameter(DESCRIPTION));
    }
}

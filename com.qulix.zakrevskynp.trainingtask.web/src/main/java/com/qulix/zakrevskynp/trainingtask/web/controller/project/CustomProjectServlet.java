package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.controller.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;

/**
 * Custom extension of @{{@link CustomServlet}} for project dao layer
 * @author Q-NZA
 */
public class CustomProjectServlet extends CustomServlet {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SHORTNAME = "shortName";
    private static final String DESCRIPTION = "description";

    /**
     * Convert map with request parameters to @{{@link Project}} object
     * @param parameters map with request parameters
     * @return @{{@link Project}} object
     */
    protected Project parametersToObject(Map<String, Object> parameters) {
        Project project = new Project();
        if (parameters.get(ID) != null) {
            project.setId((Integer) parameters.get(ID));
        }
        else {
            project.setId(null);
        }
        project.setId((Integer) parameters.get(ID));
        project.setName((String) parameters.get(NAME));
        project.setShortName((String) parameters.get(SHORTNAME));
        project.setDescription((String) parameters.get(DESCRIPTION));
        return project;
    }
}

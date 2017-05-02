package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.util.Map;

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
    private static final String SHORTNAME = "shortName";
    private static final String DESCRIPTION = "description";

    /**
     * Converts a map with request parameters to a @{{@link Project}} object
     *
     * @param parameters map with request parameters
     * @return @{{@link Project}} object
     */
    protected Project parametersToObject(Map<String, Object> parameters) {
        Integer id = null;
        if (parameters.get(ID) != null) {
            id = (Integer) parameters.get(ID);
        }
        String name = (String) parameters.get(NAME);
        String shortName = (String) parameters.get(SHORTNAME);
        String description = (String) parameters.get(DESCRIPTION);
        return new Project(id, name, shortName, description);
    }
}

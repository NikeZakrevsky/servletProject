package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.controller.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Custom servlet for constructing the @{{@link Person}} object from Map
 *
 * @author Q-NZA
 */
class CustomPersonServlet extends CustomServlet {

    private static final String ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String MIDDLE_NAME = "middleName";
    private static final String LAST_NAME = "lastName";
    private static final String POSITION = "position";

    /**
     * Converts a map with request parameters to a @{{@link Person}} object
     * 
     * @param parameters map with request parameters
     * @return @{{@link Person}} object
     */
    protected Person parametersToObject(Map<String, Object> parameters) {
        Integer id = null;
        if (parameters.get(ID) != null) {
            id = (Integer) parameters.get(ID);
        }
        String firstName = (String) parameters.get(FIRST_NAME);
        String middleName = (String) parameters.get(MIDDLE_NAME);
        String lastName = (String) parameters.get(LAST_NAME);
        String position = (String) parameters.get(POSITION);
        return new Person(id, firstName, middleName, lastName, position);
    }
}

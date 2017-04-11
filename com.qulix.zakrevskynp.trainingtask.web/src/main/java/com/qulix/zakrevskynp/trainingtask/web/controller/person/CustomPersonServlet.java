package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.controller.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Custom extension of @{{@link CustomServlet}} for person dao layer
 * @author Q-NZA
 */
class CustomPersonServlet extends CustomServlet {

    private static final String ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String MIDDLE_NAME = "middleName";
    private static final String LAST_NAME = "lastName";
    private static final String POSITION = "position";

    /**
     * Convert map with request parameters to @{{@link Person}} object
     * @param parameters map with request parameters
     * @return @{{@link Person}} object
     */
    protected Person parametersToObject(Map<String, Object> parameters) {
        Person person = new Person();
        if (parameters.get(ID) != null) {
            person.setId((Integer) parameters.get(ID));
        }
        else {
            person.setId(null);
        }
        person.setFirstName((String) parameters.get(FIRST_NAME));
        person.setMiddleName((String) parameters.get(MIDDLE_NAME));
        person.setLastName((String) parameters.get(LAST_NAME));
        person.setPosition((String) parameters.get(POSITION));
        return person;
    }
}

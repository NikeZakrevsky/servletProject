package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import javax.servlet.http.HttpServletRequest;

import com.qulix.zakrevskynp.trainingtask.web.controller.BaseHttpServlet;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Custom servlet for constructing the @{{@link Person}} object from Map
 *
 * @author Q-NZA
 */
class CustomPersonServlet extends BaseHttpServlet {

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
    protected Person parametersToObject(HttpServletRequest parameters) {
        Integer id = null;
        if (!parameters.getParameter(ID).equals("")) {
            id = Integer.parseInt(parameters.getParameter(ID));
        }
        String firstName = parameters.getParameter(FIRST_NAME);
        String middleName = parameters.getParameter(MIDDLE_NAME);
        String lastName = parameters.getParameter(LAST_NAME);
        String position = parameters.getParameter(POSITION);

        return new Person(id, firstName, middleName, lastName, position);
    }

    protected void setAttributesToRequest(HttpServletRequest request) {
        request.setAttribute(ID, request.getParameter("id"));
        request.setAttribute(FIRST_NAME, request.getParameter("firstName"));
        request.setAttribute(MIDDLE_NAME, request.getParameter("middleName"));
        request.setAttribute(LAST_NAME, request.getParameter("lastName"));
        request.setAttribute(POSITION , request.getParameter("position"));
    }

    protected void setObjectToRequest(Person person, HttpServletRequest request) {
        request.setAttribute(ID, person.getId());
        request.setAttribute(FIRST_NAME, person.getFirstName());
        request.setAttribute(MIDDLE_NAME, person.getMiddleName());
        request.setAttribute(LAST_NAME, person.getLastName());
        request.setAttribute(POSITION, person.getPosition());
    }
}

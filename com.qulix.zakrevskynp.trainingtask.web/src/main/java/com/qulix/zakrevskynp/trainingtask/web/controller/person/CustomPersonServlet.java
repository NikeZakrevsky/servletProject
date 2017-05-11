package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import javax.servlet.http.HttpServletRequest;

import com.qulix.zakrevskynp.trainingtask.web.controller.BaseHttpServlet;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * The servlet contains common methods form *Person servlets.
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
     * The method creates the @{{@link Person}} object from the http request parameters.
     * 
     * @param parameters http request parameters from the forms.
     * @return @{{@link Person}} object.
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

    /**
     * The method sets the attributes of the http request.
     * The method is used if parameters from the request are not valid.
     * 
     * @param request @{{@link HttpServletRequest}} object from setting of the attributes.
     */
    protected void setAttributesToRequest(HttpServletRequest request) {
        request.setAttribute(ID, request.getParameter("id"));
        request.setAttribute(FIRST_NAME, request.getParameter("firstName"));
        request.setAttribute(MIDDLE_NAME, request.getParameter("middleName"));
        request.setAttribute(LAST_NAME, request.getParameter("lastName"));
        request.setAttribute(POSITION , request.getParameter("position"));
    }

    /**
     * The method sets the attributes of the http request.
     * Attributes are taken from the @{{@link Person}} object.
     * The method is used if parameters from the request are valid.
     *
     * @param person @{{@link Person}} object with request attributes.
     * @param request @{{@link HttpServletRequest}} object from setting of the attributes.
     */
    protected void setObjectToRequest(Person person, HttpServletRequest request) {
        request.setAttribute(ID, person.getId());
        request.setAttribute(FIRST_NAME, person.getFirstName());
        request.setAttribute(MIDDLE_NAME, person.getMiddleName());
        request.setAttribute(LAST_NAME, person.getLastName());
        request.setAttribute(POSITION, person.getPosition());
    }
}

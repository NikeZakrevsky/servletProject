package com.qulix.zakrevskynp.trainingtask.web.controller.person;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Show view with form for editing new person and handling it data
 * @author Q-NZA
 */
@WebServlet("/editPerson")
public class EditPersonServlet extends CustomPersonServlet {

    private PersonDAO personDAO = new PersonDAOImpl(Person.class);
    private static final String ID = "id";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> parameters = getParametersFromRequest(request);
        List<String> errors = new PersonDataValidator().validate(parameters);
        if (errors.isEmpty()) {
            Person person = parametersToObject(parameters);
            personDAO.updatePerson(person);
            response.sendRedirect(Attribute.REDIRECT_PERSON_LIST);
        }
        else {
            request.setAttribute(Attribute.PERSON_OBJECT_NAME, parameters);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            request.setAttribute(Attribute.ACTION, Attribute.EDIT_PERSON);
            request.getRequestDispatcher(Attribute.PERSON_VIEW).forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Person person  = personDAO.getPersonById(Integer.parseInt(request.getParameter(ID)));
        request.setAttribute(Attribute.PERSON_OBJECT_NAME, person);
        request.setAttribute(Attribute.ACTION, Attribute.EDIT_PERSON);
        request.getRequestDispatcher(Attribute.PERSON_VIEW).forward(request, response);
    }
}

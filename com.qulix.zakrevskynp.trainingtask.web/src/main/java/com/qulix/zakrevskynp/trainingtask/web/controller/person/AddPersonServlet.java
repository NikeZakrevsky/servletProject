package com.qulix.zakrevskynp.trainingtask.web.controller.person;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Shows the form of adding a new person and processing its data
 *
 * @author Q-NZA
 */
@WebServlet("/addPerson")
public class AddPersonServlet extends CustomPersonServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> parameters = getParametersFromRequest(request);
        List<String> errors = new PersonDataValidator().validate(parameters);
        if (errors.isEmpty()) {
            Person person = parametersToObject(parameters);
            new PersonDAOImpl().add(person);
            response.sendRedirect(Attribute.REDIRECT_PERSON_LIST);
        }
        else {
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            request.setAttribute(Attribute.PERSON_OBJECT_NAME, parameters);
            request.setAttribute(Attribute.ACTION, Attribute.ADD_PERSON);
            request.getRequestDispatcher(Attribute.PERSON_VIEW).forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(Attribute.ACTION, Attribute.ADD_PERSON);
        request.getRequestDispatcher(Attribute.PERSON_VIEW).forward(request, response);
    }
}
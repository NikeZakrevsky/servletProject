package com.qulix.zakrevskynp.trainingtask.web.controller.person;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;

/**
 * Show view with form for adding new person and handling it data
 * @author Q-NZA
 */
@WebServlet("/addPerson")
public class AddPersonServlet extends CustomPersonServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> parameters = getParametersFromRequest(request);
        List<String> errors = new PersonDataValidator().validate(parameters);
        if (errors.isEmpty()) {
            Person person = parametersToObject(parameters);
            new PersonDAOImpl().addPerson(person);
            response.sendRedirect("personsList");
        }
        else {
            request.setAttribute("errors", errors);
            request.setAttribute("person", parameters);
            request.setAttribute("action", "addPerson");
            request.getRequestDispatcher("view/personView.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", "addPerson");
        request.getRequestDispatcher("view/personView.jsp").forward(request, response);
    }
}
package com.qulix.zakrevskynp.trainingtask.web.person.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.person.Person;
import com.qulix.zakrevskynp.trainingtask.web.person.PersonDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;

/**
 * Show view with form for adding new person and handling it data
 * @author Q-NZA
 */
   
@WebServlet("/addPerson")
public class AddPersonServlet extends CustomPersonServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> parameters = getParametersFromRequest(request);
        List<String> errors = new PersonDataValidator().validate(parameters);
        if (errors.size() == 0) {
            Person person = parametersToObject(parameters);
            new PersonDAOImpl().addPerson(person);
            response.sendRedirect("personsList");
        }
        else {
            request.setAttribute("errors", errors);
            request.setAttribute("person", parameters);
            request.setAttribute("action", "addPerson");
            request.getRequestDispatcher("personView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", "addPerson");
        request.getRequestDispatcher("personView.jsp").forward(request, response);
    }
}
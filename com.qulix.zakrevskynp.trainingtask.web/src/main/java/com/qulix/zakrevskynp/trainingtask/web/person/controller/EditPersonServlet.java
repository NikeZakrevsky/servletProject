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
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;

/**
 * Show view with form for editing new person and handling it data
 * @author Q-NZA
 */
@WebServlet("/editPerson")
public class EditPersonServlet extends CustomPersonServlet {

    private PersonDAO personDAO = new PersonDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> parameters = getParametersFromRequest(request);

        List<String> errors = new PersonDataValidator().validate(parameters);
        if (errors.size() == 0) {
            Person person = parametersToObject(parameters);
            personDAO.updatePerson(person);
            response.sendRedirect("personsList");
        }
        else {
            request.setAttribute("person", parameters);
            request.setAttribute("errors", errors);
            request.setAttribute("action", "editPerson");
            request.getRequestDispatcher("personView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Person person  = personDAO.getPersonById(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("person", person);
        request.setAttribute("action", "editPerson");
        request.getRequestDispatcher("personView.jsp").forward(request, response);
    }
}

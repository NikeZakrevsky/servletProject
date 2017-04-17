package com.qulix.zakrevskynp.trainingtask.web.controller.person;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;

/**
 * Show view with form for editing new person and handling it data
 * @author Q-NZA
 */
@WebServlet("/editPerson")
public class EditPersonServlet extends CustomPersonServlet {

    private PersonDAO personDAO = new PersonDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> parameters = getParametersFromRequest(request);
        List<String> errors = new PersonDataValidator().validate(parameters);
        if (errors.isEmpty()) {
            Person person = parametersToObject(parameters);
            personDAO.updatePerson(person);
            response.sendRedirect("personsList");
        }
        else {
            request.setAttribute("person", parameters);
            request.setAttribute("errors", errors);
            request.setAttribute("action", "editPerson");
            request.getRequestDispatcher("view/personView.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Person person  = personDAO.getPersonById(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("person", person);
        request.setAttribute("action", "editPerson");
        request.getRequestDispatcher("view/personView.jsp").forward(request, response);
    }
}

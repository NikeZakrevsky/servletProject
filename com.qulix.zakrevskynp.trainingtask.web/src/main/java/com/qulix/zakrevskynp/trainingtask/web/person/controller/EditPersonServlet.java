package com.qulix.zakrevskynp.trainingtask.web.person.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.person.Person;
import com.qulix.zakrevskynp.trainingtask.web.person.PersonDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;

/**
 * Show view with form for editing new person and handling it data
 * @author Q-NZA
 */
@WebServlet("/editPerson")
public class EditPersonServlet extends CustomServlet {

    private Logger logger = Logger.getLogger(EditPersonServlet.class.getName());
    private List<String> errors = new ArrayList<>();
    private PersonDAO personDAO = new PersonDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Map<String, Object> parameters = getParametersFromRequest(request);

        errors = new PersonDataValidator().validate(parameters);
        if (errors.size() == 0) {
            try {
                personDAO.updatePerson(parameters);
                response.sendRedirect("personsList");
            } catch (CustomException e) {
                logger.log(Level.SEVERE, e.getMessage());
                errors.clear();
                errors.add(e.getMessage());
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("personView.jsp").forward(request, response);
            }
        }
        else {
            request.setAttribute("person", parameters);
            request.setAttribute("errors", errors);
            request.setAttribute("action", "editPerson");
            request.getRequestDispatcher("personView.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Person person  = personDAO.getPersonById(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("person", person);
            request.setAttribute("action", "editPerson");
            request.getRequestDispatcher("personView.jsp").forward(request, response);
        } catch (CustomException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.getRequestDispatcher("personsList.jsp").forward(request, response);
        }
    }
}

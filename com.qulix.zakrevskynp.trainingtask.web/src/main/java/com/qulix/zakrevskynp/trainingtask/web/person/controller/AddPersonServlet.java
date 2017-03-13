package com.qulix.zakrevskynp.trainingtask.web.person.controller;

import java.io.IOException;
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
import com.qulix.zakrevskynp.trainingtask.web.person.PersonDataValidator;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;

/**
 * Show view with form for adding new person and handling it data
 * @author Q-NZA
 */
   
@WebServlet("/addPerson")
public class AddPersonServlet extends CustomServlet {

    private Logger logger = Logger.getLogger(AddPersonServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Map<String, Object> parameters = getParametersFromRequest(request);
        List<String> errors = new PersonDataValidator().validate(parameters);

        if (errors.size() == 0) {
            try {
                new PersonDAOImpl().addPerson(parameters);
                response.sendRedirect("personsList");
            } catch (CustomException e) {
                logger.log(Level.SEVERE, e.getMessage());
                errors.clear();
                errors.add(e.getMessage());
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("personsList.jsp").forward(request, response);
            }
        }
        else {
            request.setAttribute("errors", errors);
            request.setAttribute("person", parameters);
            request.setAttribute("action", "addPerson");
            request.getRequestDispatcher("personView.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", "addPerson");
        request.getRequestDispatcher("personView.jsp").forward(request, response);
    }


}
package com.qulix.zakrevskynp.trainingtask.web.person.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.person.Person;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;

/**
 * Show view with list of person
 * @author Q-NZA
 */
@WebServlet("/personsList")
public class PersonsListServlet extends CustomServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(PersonsListServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonDAO personDAO = new PersonDAOImpl();
        try {
            List<Person> personsList = personDAO.getPersonsList();
            request.setAttribute("error", request.getSession().getAttribute("error"));
            request.setAttribute("persons", personsList);
            request.getRequestDispatcher("personsList.jsp").forward(request, response);
        } catch (CustomException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("personsList.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
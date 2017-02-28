package com.qulix.zakrevskynp.trainingtask.controller.person;

import com.qulix.zakrevskynp.trainingtask.dao.person.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.model.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Show view with list of person
 */
@WebServlet("/personsList")
public class PersonsListServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(PersonsListServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonDAO personDAO = new PersonDAOImpl();
        List<Person> personsList = null;
        try {
            personsList = personDAO.getPersonsList();
        } catch (DAOException e) {
            logger.log(Level.SEVERE, e.getCause().toString());
            errors.clear();
            errors.add(e.getCause().getMessage());
            request.setAttribute("error",errors);
            request.getRequestDispatcher("personsList.jsp").forward(request,response);
        }
        request.setAttribute("error", request.getSession().getAttribute("error"));
        request.setAttribute("persons", personsList);
        request.getRequestDispatcher("personsList.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

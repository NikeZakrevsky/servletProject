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
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;

/**
 * Handling remove person action
 * @author Q-NZA
 */
@WebServlet("/removePerson")
public class RemovePersonServlet extends CustomServlet {
    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(RemovePersonServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonDAO personDAO = new PersonDAOImpl();
        try {
            personDAO.removePerson(Integer.parseInt(request.getParameter("id")));
            response.sendRedirect("personsList");
        }
        catch (CustomException e) {
            logger.log(Level.SEVERE, e.getMessage());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("personsList.jsp").forward(request, response);
        }

    }
}
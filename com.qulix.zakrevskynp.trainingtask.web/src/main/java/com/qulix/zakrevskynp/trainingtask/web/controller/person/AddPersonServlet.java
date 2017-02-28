package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.util.PersonDataValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Show view with form for adding new person and handling it data
 */
@WebServlet("/addPerson")
public class AddPersonServlet extends HttpServlet {

    private PersonDAO personDAO = new PersonDAOImpl();
    private Logger logger = Logger.getLogger(AddPersonServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonDataValidator validator = new PersonDataValidator();
        List<String> errors = validator.validate(request.getParameter("fname"), request.getParameter("sname"), request.getParameter("lname"), request.getParameter("position"));
        if (errors.size() == 0) {
            try {
                personDAO.addPerson(request.getParameter("fname"), request.getParameter("sname"), request.getParameter("lname"), request.getParameter("position"));
            } catch (DAOException e) {
                logger.log(Level.SEVERE, e.getMessage());
                errors.clear();
                errors.add(e.getCause().getMessage());
                request.setAttribute("error",errors);
                request.getRequestDispatcher("personsList.jsp").forward(request,response);
            }
            response.sendRedirect("personsList");
        }
        else {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("personView.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", "addPerson");
        request.getRequestDispatcher("personView.jsp").forward(request, response);
    }
}
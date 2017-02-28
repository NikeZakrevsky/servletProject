package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.util.PersonDataValidator;

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
 * Show view with form for editing new person and handling it data
 */
@WebServlet("/editPerson")
public class EditPersonServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(EditPersonServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonDAO personDAO = new PersonDAOImpl();
        PersonDataValidator validator = new PersonDataValidator();
        errors = validator.validate(request.getParameter("fname"), request.getParameter("sname"), request.getParameter("lname"), request.getParameter("position"));
        if (errors.size() == 0) {
            try {
                personDAO.updatePerson(Integer.parseInt(request.getParameter("id")), request.getParameter("fname"), request.getParameter("sname"), request.getParameter("lname"), request.getParameter("position"));
                response.sendRedirect("personsList");
            } catch (DAOException e) {
                logger.log(Level.SEVERE, e.getMessage());
                errors.clear();
                errors.add(e.getCause().getMessage());
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("personView.jsp").forward(request,response);
            }
        }
        else {
            response.sendRedirect("editPerson?id=" + request.getParameter("id"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!request.getParameter("id").equals("")) {
            PersonDAO personDAO = new PersonDAOImpl();
            Person person = null;
            try {
                person = personDAO.getPersonById(Integer.parseInt(request.getParameter("id")));
            } catch (DAOException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
                errors.clear();
                errors.add(e.getCause().getMessage());
                request.getRequestDispatcher("personsList.jsp").forward(request, response);
            }
            request.setAttribute("person", person);
            request.setAttribute("errors", errors);
            request.setAttribute("action", "editPerson");
            request.getRequestDispatcher("personView.jsp").forward(request, response);
        } else {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("personView.jsp").forward(request, response);
        }
    }
}

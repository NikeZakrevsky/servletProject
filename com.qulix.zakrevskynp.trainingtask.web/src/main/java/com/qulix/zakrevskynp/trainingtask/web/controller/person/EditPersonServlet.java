package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.dao.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.validator.PersonDataValidator;

/**
 * Show view with form for editing new person and handling it data
 * @author Q-NZA
 */
@WebServlet("/editPerson")
public class EditPersonServlet extends HttpServlet {

    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(EditPersonServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PersonDAO personDAO = new PersonDAOImpl();
        PersonDataValidator validator = new PersonDataValidator();

        List<String> parametersNames = Collections.list(request.getParameterNames());
        Map<String, Object> parameters = parametersNames.stream().collect(Collectors.toMap(x -> x, request::getParameter));

        errors = validator.validate(parameters);
        if (errors.size() == 0) {
            try {
                personDAO.updatePerson(parameters);
                response.sendRedirect("personsList");
            } catch (DAOException e) {
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
        if (!request.getParameter("id").equals("")) {
            PersonDAO personDAO = new PersonDAOImpl();
            Person person = null;
            try {
                person = personDAO.getPersonById(Integer.parseInt(request.getParameter("id")));
            } catch (DAOException e) {
                logger.log(Level.SEVERE, e.getCause().toString());
                errors.clear();
                errors.add(e.getMessage());
                request.getRequestDispatcher("personsList.jsp").forward(request, response);
            }
            request.setAttribute("person", person);
            request.setAttribute("action", "editPerson");
            request.getRequestDispatcher("personView.jsp").forward(request, response);
        } else {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("personView.jsp").forward(request, response);
        }
    }
}

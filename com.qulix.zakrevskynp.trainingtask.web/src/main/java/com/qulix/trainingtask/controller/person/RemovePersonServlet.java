package com.qulix.trainingtask.controller.person;

import com.qulix.trainingtask.dao.person.PersonDAO;
import com.qulix.trainingtask.dao.person.PersonDAOImpl;
import com.qulix.trainingtask.dao.exception.DAOException;

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
 * Handling remove person action
 */
@WebServlet("/removePerson")
public class RemovePersonServlet extends HttpServlet {
    private List<String> errors = new ArrayList<>();
    private Logger logger = Logger.getLogger(RemovePersonServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PersonDAO personDAO = new PersonDAOImpl();
        try {
            personDAO.removePerson(Integer.parseInt(request.getParameter("id")));
        }
        catch(DAOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            errors.clear();
            errors.add(e.getCause().getMessage());
            request.setAttribute("error",errors);
            request.getRequestDispatcher("personsList.jsp").forward(request,response);
            logger.log(Level.SEVERE, e.getCause().toString());
        }
        response.sendRedirect("personsList");
    }
}

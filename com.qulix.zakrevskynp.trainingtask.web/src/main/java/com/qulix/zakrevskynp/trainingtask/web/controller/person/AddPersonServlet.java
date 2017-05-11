package com.qulix.zakrevskynp.trainingtask.web.controller.person;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDao;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Shows the form of adding a new person and processing its data.
 *
 * @author Q-NZA
 */
@WebServlet("/addPerson")
public class AddPersonServlet extends CustomPersonServlet {

    /**
     * Form data processing.
     *
     * @param request http request with form data.
     * @param response response object.
     * @throws ServletException servlet exception.
     * @throws IOException input/output exception.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonDataValidator personDataValidator = new PersonDataValidator();
        List<String> errors = personDataValidator.validate(request);

        if (errors.isEmpty()) {
            Person person = parametersToObject(request);
            new PersonDao().add(person);

            response.sendRedirect(Attribute.REDIRECT_PERSON_LIST);
        }
        else {
            setAttributesToRequest(request);
            request.setAttribute(Attribute.ERROR_LIST_NAME, errors);
            request.setAttribute(Attribute.ACTION, Attribute.ADD_PERSON);

            request.getRequestDispatcher(Attribute.PERSON_VIEW).forward(request, response);
        }
    }

    /**
     * Displays a page with a form.
     *
     * @param request http request with form data.
     * @param response response object.
     * @throws ServletException servlet exception.
     * @throws IOException input/output exception.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(Attribute.ACTION, Attribute.ADD_PERSON);

        request.getRequestDispatcher(Attribute.PERSON_VIEW).forward(request, response);
    }
}
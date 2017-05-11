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
 * The servlet displays the form of creating a person and processes the entered data.
 *
 * @author Q-NZA
 */
@WebServlet("/addPerson")
public class AddPersonServlet extends CustomPersonServlet {

    /**
     * The method receives data from a form, validates it and adds the person's data to the database.
     * In the case of validation errors, its displayed to the user.
     * If there are no validation errors, the list of persons is displayed.
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
     * The method displays a form for adding a person.
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
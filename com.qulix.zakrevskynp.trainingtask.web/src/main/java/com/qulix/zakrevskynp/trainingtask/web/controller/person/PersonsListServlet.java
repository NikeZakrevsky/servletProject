package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDao;

/**
 * The servlet displays the list of the persons.
 *
 * @author Q-NZA
 */
@WebServlet("/personsList")
public class PersonsListServlet extends CustomPersonServlet {

    /**
     * The method displays the page with list of the person.
     *
     * @param request http request with form data.
     * @param response response object.
     * @throws ServletException servlet exception.
     * @throws IOException input/output exception.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List personsList = new PersonDao().getAll();

        request.setAttribute(Attribute.ERROR_LIST_NAME, request.getSession().getAttribute(Attribute.ERROR_LIST_NAME));
        request.setAttribute(Attribute.PERSONS_LIST_NAME, personsList);

        request.getRequestDispatcher(Attribute.PERSONS_LIST_VIEW).forward(request, response);
    }
}

package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDao;

/**
 * The servlet removes a person from the database.
 * 
 * @author Q-NZA
 */
@WebServlet("/removePerson")
public class RemovePersonServlet extends CustomPersonServlet {
    private static final String ID = "id";

    /**
     * The method removes a person from the database by id and displays the list of the persons.
     *
     * @param request http request with form data.
     * @param response response object.
     * @throws ServletException servlet exception.
     * @throws IOException input/output exception.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new PersonDao().remove(Integer.parseInt(request.getParameter(ID)));

        response.sendRedirect(Attribute.REDIRECT_PERSON_LIST);
    }
}

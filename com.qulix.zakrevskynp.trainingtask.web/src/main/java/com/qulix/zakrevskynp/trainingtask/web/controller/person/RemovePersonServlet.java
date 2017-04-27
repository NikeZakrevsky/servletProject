package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDaoImpl;

/**
 * Handling the action of removing a person and redirects to the @{{@link PersonsListServlet}}
 * 
 * @author Q-NZA
 */
@WebServlet("/removePerson")
public class RemovePersonServlet extends CustomPersonServlet {

    private static final String ID = "id";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new PersonDaoImpl().remove(Integer.parseInt(request.getParameter(ID)));
        response.sendRedirect(Attribute.REDIRECT_PERSON_LIST);
    }
}

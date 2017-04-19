package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;

/**
 * Handling remove person action
 * @author Q-NZA
 */
@WebServlet("/removePerson")
public class RemovePersonServlet extends CustomPersonServlet {
    private static final String ID = "id";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonDAO personDAO = new PersonDAOImpl();
        personDAO.removePerson(Integer.parseInt(request.getParameter(ID)));
        response.sendRedirect(Attribute.REDIRECT_PERSON_LIST);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

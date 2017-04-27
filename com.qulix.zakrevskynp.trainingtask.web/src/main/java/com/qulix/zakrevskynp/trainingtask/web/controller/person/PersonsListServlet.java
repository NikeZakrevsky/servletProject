package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDaoImpl;

/**
 * Shows a page with all persons from the database
 *
 * @author Q-NZA
 */
@WebServlet("/personsList")
public class PersonsListServlet extends CustomPersonServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List personsList = new PersonDaoImpl().getAll();
        request.setAttribute(Attribute.ERROR_LIST_NAME, request.getSession().getAttribute(Attribute.ERROR_LIST_NAME));
        request.setAttribute(Attribute.PERSONS_LIST_NAME, personsList);
        request.getRequestDispatcher(Attribute.PERSONS_LIST_VIEW).forward(request, response);
    }
}

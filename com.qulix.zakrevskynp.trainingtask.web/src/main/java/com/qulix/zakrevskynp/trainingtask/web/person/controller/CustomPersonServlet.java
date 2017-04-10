package com.qulix.zakrevskynp.trainingtask.web.person.controller;

import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.person.Person;

class CustomPersonServlet extends CustomServlet {

    private static final String ID = "id";
    private static final String FIRST_NAME = "fname";
    private static final String MIDDLE_NAME = "sname";
    private static final String LAST_NAME = "lname";
    private static final String POSITION = "position";

    protected Person parametersToObject(Map<String, Object> parameters) {
        Person person = new Person();
        if (parameters.get(ID) != null) {
            person.setId((Integer) parameters.get(ID));
        }
        else {
            person.setId(null);
        }
        person.setFname((String) parameters.get(FIRST_NAME));
        person.setSname((String) parameters.get(MIDDLE_NAME));
        person.setLname((String) parameters.get(LAST_NAME));
        person.setPosition((String) parameters.get(POSITION));
        return person;
    }
}

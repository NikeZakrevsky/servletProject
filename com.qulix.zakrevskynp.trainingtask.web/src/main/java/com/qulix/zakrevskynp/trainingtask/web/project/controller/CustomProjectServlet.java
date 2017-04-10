package com.qulix.zakrevskynp.trainingtask.web.project.controller;

import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.project.Project;

public class CustomProjectServlet extends CustomServlet {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SHORTNAME = "shortName";
    private static final String DESCRIPTION = "description";

    protected Project parametersToObject(Map<String, Object> parameters) {
        Project project = new Project();
        if (parameters.get(ID) != null) {
            project.setId((Integer) parameters.get(ID));
        }
        else {
            project.setId(null);
        }
        project.setId((Integer) parameters.get(ID));
        project.setName((String) parameters.get(NAME));
        project.setShortName((String) parameters.get(SHORTNAME));
        project.setDescription((String) parameters.get(DESCRIPTION));
        return project;
    }
}

package com.qulix.zakrevskynp.trainingtask.web.task.controller;

import java.sql.Date;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.task.Task;

public class CustomTaskServlet extends CustomServlet {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TIME = "time";
    private static final String STARTDATE = "startDate";
    private static final String ENDDATE = "endDate";
    private static final String STATUS = "status";
    private static final String PROJECTID = "projectId";
    private static final String PERSONID = "personId";
    private static final String SHORTNAME = "shortname";
    private static final String PERSON = "person";

    protected Task parametersToObject(Map<String, Object> parameters) {
        Task task = new Task();
        task.setId((Integer) parameters.get(ID));
        task.setName((String) parameters.get(NAME));
        task.setTime((Integer) parameters.get(TIME));
        task.setStartDate((Date) parameters.get(STARTDATE));
        task.setEndDate((Date) parameters.get(ENDDATE));
        task.setStatus((String) parameters.get(STATUS));
        task.setProjectId((Integer) parameters.get(PROJECTID));
        task.setPersonId((Integer) parameters.get(PERSONID));
        task.setProjectShortName(SHORTNAME);
        task.setPerformer((String) parameters.get(PERSON));
        return task;
    }
}

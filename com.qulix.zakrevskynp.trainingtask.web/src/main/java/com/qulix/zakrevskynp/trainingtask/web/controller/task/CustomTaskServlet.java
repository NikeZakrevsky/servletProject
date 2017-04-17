package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.sql.Date;
import java.time.Duration;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.controller.CustomServlet;
import com.qulix.zakrevskynp.trainingtask.web.model.TaskStatus;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Custom extension of @{{@link CustomServlet}} for task dao layer
 * @author Q-NZA
 */
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

    /**
     * Convert map with request parameters to @{{@link Task}} object
     * @param parameters map with request parameters
     * @return @{{@link Task}} object
     */
    protected Task parametersToObject(Map<String, Object> parameters) {
        Integer id = null;
        if (parameters.get(ID) != null) {
            id = (Integer) parameters.get(ID);
        }

        String name = (String) parameters.get(NAME);
        Duration workTime = (Duration) parameters.get(TIME);
        Date startDate = (Date) parameters.get(STARTDATE);
        Date endDate = (Date) parameters.get(ENDDATE);
        TaskStatus status = TaskStatus.valueOf(parameters.get(STATUS).toString());
        Integer projectId = (Integer) parameters.get(PROJECTID);
        Integer personId = (Integer) parameters.get(PERSONID);
        String projectShortName = (String) parameters.get(SHORTNAME);
        String performer = (String) parameters.get(PERSON);

        return new Task(id, name, workTime, startDate, endDate, status, performer,  projectShortName, projectId, personId);
    }
}

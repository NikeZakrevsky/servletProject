package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.sql.Date;
import java.time.Duration;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.controller.CustomServlet;
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
        Task task = new Task();
        if (parameters.get(ID) != null) {
            task.setId((Integer) parameters.get(ID));
        }
        else {
            task.setId(null);
        }
        task.setId((Integer) parameters.get(ID));
        task.setName((String) parameters.get(NAME));
        task.setTime(Duration.ofHours((long) (int)parameters.get(TIME)));
        task.setStartDate((Date) parameters.get(STARTDATE));
        task.setEndDate((Date) parameters.get(ENDDATE));
        task.setStatus(parameters.get(STATUS).toString());
        task.setProjectId((Integer) parameters.get(PROJECTID));
        task.setPersonId((Integer) parameters.get(PERSONID));
        task.setProjectShortName(SHORTNAME);
        task.setPerformer((String) parameters.get(PERSON));
        return task;
    }
}

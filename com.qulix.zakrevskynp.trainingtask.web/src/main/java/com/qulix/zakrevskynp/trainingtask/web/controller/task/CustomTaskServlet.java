package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.sql.Date;
import java.time.Duration;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.controller.BaseHttpServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDao;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.model.TaskStatus;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Custom servlet for constructing a @{{@link Task}} object from Map
 *
 * @author Q-NZA
 */
public class CustomTaskServlet extends BaseHttpServlet {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TIME = "workTime";
    private static final String STARTDATE = "startDate";
    private static final String ENDDATE = "endDate";
    private static final String STATUS = "taskStatus";
    private static final String PROJECTID = "projectId";
    private static final String PERSONID = "personId";
    private static final String SHORTNAME = "shortname";
    private static final String PERSON = "person";

    /**
     * Converts a map with request parameters to a @{{@link Task}} object
     *
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
        TaskStatus status = TaskStatus.fromString(parameters.get(STATUS).toString());
        Integer projectId = (Integer) parameters.get(PROJECTID);
        Integer personId = (Integer) parameters.get(PERSONID);
        String projectShortName = (String) parameters.get(SHORTNAME);
        Person person = null;
        if (personId != null) {
            person = new PersonDao().get(personId);
        }
        Task task = new Task(id, name, workTime, startDate, endDate, status, person);
        task.setProjectId(projectId);
        task.setPerson(person);
        task.setProjectShortName(projectShortName);
        return task;
    }
}

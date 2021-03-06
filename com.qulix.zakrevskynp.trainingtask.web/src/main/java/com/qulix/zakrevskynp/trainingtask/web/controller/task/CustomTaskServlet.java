package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.qulix.zakrevskynp.trainingtask.web.controller.BaseHttpServlet;
import com.qulix.zakrevskynp.trainingtask.web.dao.PersonDao;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;
import com.qulix.zakrevskynp.trainingtask.web.model.TaskStatus;

/**
 * The servlet contains common methods for *Task servlets.
 *
 * @author Q-NZA
 */
public class CustomTaskServlet extends BaseHttpServlet {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String WORK_TIME = "workTime";
    private static final String STARTDATE = "startDate";
    private static final String ENDDATE = "endDate";
    private static final String STATUS = "taskStatus";
    private static final String PROJECT_ID_DISABLED = "projectIdDisabled";
    private static final String PROJECT_ID = "projectId";
    private static final String PERSONID = "personId";
    private static final String SHORTNAME = "shortname";
    private static final String PERSON = "person";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * The method creates the @{{@link Task}} object from the http request parameters.
     *
     * @param task task with request attributes.
     * @param request request for setting attributes.
     */
    public void setObjectToRequest(Task task, HttpServletRequest request) {
        request.setAttribute(ID, task.getId());
        request.setAttribute(NAME, task.getName());
        request.setAttribute(WORK_TIME, task.getWorkTime());
        request.setAttribute(STARTDATE, task.getStartDate());
        request.setAttribute(ENDDATE , task.getEndDate());
        request.setAttribute(STATUS , task.getStatus());
        request.setAttribute(PROJECT_ID, task.getProjectId());
        request.setAttribute(PERSON , task.getPerson());
    }

    /**
     * The method sets the attributes of the http request.
     * The method is used if parameters from the request are not valid.
     *
     * @param request @{{@link HttpServletRequest}} object from setting of the attributes.
     */
    protected void setAttributesToRequest(HttpServletRequest request) {
        request.setAttribute(ID, request.getParameter(ID));
        request.setAttribute(NAME, request.getParameter(NAME));
        if (!request.getParameter(WORK_TIME).equals("")) {
            request.setAttribute(WORK_TIME, Duration.ofMinutes((long) (int) (Float.parseFloat(request.getParameter(WORK_TIME))
                * 60)));
        }
        request.setAttribute(STARTDATE, request.getParameter(STARTDATE));
        request.setAttribute(ENDDATE , request.getParameter(ENDDATE));
        request.setAttribute(STATUS , request.getParameter(STATUS));
        request.setAttribute(PROJECT_ID, request.getParameter(PROJECT_ID_DISABLED));
        request.setAttribute(PERSONID , request.getParameter(PERSONID));
    }

    /**
     * The method creates the @{{@link Task}} object from the http request parameters.
     *
     * @param parameters map with request parameters.
     * @return @{{@link Task}} object.
     */
    protected Task parametersToObject(HttpServletRequest parameters) {
        Integer id = null;
        if (!parameters.getParameter(ID).equals("")) {
            id = Integer.parseInt(parameters.getParameter(ID));
        }
        String name = parameters.getParameter(NAME);
        Duration workTime = Duration.ofMinutes((long) (int) (Float.parseFloat(parameters.getParameter(WORK_TIME)) * 60));
        Date startDate = stringToDate(parameters.getParameter(STARTDATE));
        Date endDate = stringToDate(parameters.getParameter(ENDDATE));
        TaskStatus status = TaskStatus.fromString(parameters.getParameter(STATUS));
        Integer projectId = null;
        if (parameters.getParameter(PROJECT_ID_DISABLED) != null && !parameters.getParameter(PROJECT_ID_DISABLED).equals("")) {
            projectId = Integer.parseInt(parameters.getParameter(PROJECT_ID_DISABLED));
        }
        if (parameters.getParameter(PROJECT_ID) != null && !parameters.getParameter(PROJECT_ID).equals("")) {
            projectId = Integer.parseInt(parameters.getParameter(PROJECT_ID));
        }
        Integer personId = null;
        if (!parameters.getParameter(PERSONID).equals("")) {
            personId = Integer.parseInt(parameters.getParameter(PERSONID));
        }
        String projectShortName = parameters.getParameter(SHORTNAME);

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

    private Date stringToDate(String field) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);
        java.util.Date date = null;
        try {
            date = dateFormat.parse(field);
        } catch (ParseException e) {
            //
        }

        return date;
    }
}

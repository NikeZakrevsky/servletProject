package com.qulix.zakrevskynp.trainingtask.web.dao.task;

import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Utilites for tasks dao layer
 */
public class TaskUtil {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TIME = "time";
    private static final String STARTDATE = "start_date";
    private static final String ENDDATE = "end_date";
    private static final String STATUS = "status";
    private static final String PROJECTID = "project_id";
    private static final String PERSONID = "person_id";
    private static final String SHORTNAME = "shortname";
    private static final String PERSON = "person";

    /**
     * Create Task object from ResultSet
     *
     * @param resultSet resultSet for converting to object
     * @return created task object
     * @throws SQLException
     */
    public Task resultSetAsObject(ResultSet resultSet) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getInt(ID));
        task.setName(resultSet.getString(NAME));
        task.setTime(resultSet.getInt(TIME));
        task.setStartDate(resultSet.getDate(STARTDATE));
        task.setEndDate(resultSet.getDate(ENDDATE));
        task.setStatus(resultSet.getString(STATUS));
        task.setProjectId(resultSet.getInt(PROJECTID));
        task.setPersonId(resultSet.getInt(PERSONID));
        task.setProjectShortName(resultSet.getString(SHORTNAME));
        task.setPerformer(resultSet.getString(PERSON));
        return task;
    }

    /**
     *
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param task {@link Task} object
     * @throws SQLException
     */
    public void setPreparedStatement(PreparedStatement preparedStatement, Task task) throws SQLException {
        preparedStatement.setString(1, task.getName());
        preparedStatement.setInt(2, task.getTime());
        preparedStatement.setDate(3, task.getStartDate());
        preparedStatement.setDate(4, task.getEndDate());
        preparedStatement.setString(5, task.getStatus());
        preparedStatement.setObject(6, task.getProjectId());
        preparedStatement.setObject(7, task.getPersonId());
    }

    /**
     * Convert string to date
     *
     * @param sourceDate string for converting
     * @return converted Date object
     */
    public Date toDate(String sourceDate) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyy-MM-dd");
        java.util.Date date = null;
        try {
            date = sdf1.parse(sourceDate);
        } catch (ParseException e) {
            throw e;
        }
        return new java.sql.Date(date.getTime());
    }
}
